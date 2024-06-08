  <template>
    <div class="user-table">
      <div class="filters">
        <select v-model="filterUserType">
          <option value="all">Todos los usuarios</option>
          <option value="ADMIN">Administradores</option>
          <option value="ORGANIZATION">Organizaciones</option>
          <option value="CLIENT">Clientes</option>
        </select>
        <input type="text" v-model="searchQuery" placeholder="Buscar por nombre">
      </div>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th v-for="header in headers" :key="header.text" :class="{ sortable: header.sortable }" @click="sortUsers(header.text)">
                {{ header.text }}
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in paginatedUsers" :key="user.id" :class="{ 'disabled-row': !user.enable }">
              <td v-for="header in headers" :key="header.text">
                <!-- Si el accessor es 'username' y el rol es 'ORGANIZATION', mostrar un router-link -->
                <template v-if="header.accessor === 'username' && user.role === 'ORGANIZATION'">
                  <router-link :to="{ name: 'user-info', params: { id: user.id }}">
                    {{ user[header.accessor] }}
                  </router-link>
                </template>
                <!-- Si no, solo mostrar el valor -->
                <template v-else>
                  {{ user[header.accessor] }}
                </template>
              </td>
              <td>
                <div v-for="action in rowActions" :key="action.icon">
                  <button @click="deleteUser(user)" class="actionButton">
                    <i class="fas fa-fw" :class="action.icon"></i>
                  </button>
                </div>
              </td>
            </tr>
            <tr v-if="!paginatedUsers.length">
              <td :colspan="headers.length">{{ emptyText }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <div v-if="pagination" class="pagination">
        <button @click="prevPage" :disabled="currentPage === 1">Previous</button>
        <span>{{ currentPage }} / {{ totalPages }}</span>
        <button @click="nextPage" :disabled="currentPage === totalPages">Next</button>
      </div>

      <div v-if="isLoading" class="loading">
        Loading...
      </div>

      <div v-if="error" class="error">
        {{ error }}
      </div>
    </div>

    <div v-if="showConfirmationBanner" class="overlay">
      <div class="confirmation-banner">
        <p>¿Estás seguro de que deseas eliminar al usuario?</p>
        <button @click="confirmDeleteUser">Sí</button>
        <button @click="cancelDeleteUser">No</button>
      </div>
    </div>
  </template>
    

    <script lang="ts">
    import { User } from '@/models/User';
    import { AdminService } from '@/services/admin.service';
    import { ref, computed, defineComponent, PropType, watch } from 'vue';
    export default defineComponent( {
      name:'userTables',
      props: {
        users: {
          type: Array as PropType<User[]>,
          required: true,
          default: () => [] as User[],
        },
        headers: {
          type: Array,
          default: () => [
            { text: 'ID', align: 'left', sortable: true, accessor: 'id' },
            { text: 'Nombre', align: 'left', sortable: true, accessor: 'username' },
            { text: 'Email', align: 'left', sortable: true, accessor: 'email' },
            { text: 'Rol', align: 'left', sortable: true, accessor: 'role' },
          ],
        },
        rowActions: {
          type: Array,
          default: () => [{ icon: 'bi bi-trash', tooltip: 'Editar', handler: (user: User) => console.log('Delete User:', user) }, // Example action
  ],
        },
        emptyText: {
          type: String,
          default: 'No hay usuarios para mostrar',
        },
        pagination: {
          type: Boolean,
          default: false,
        },
        paginationOptions: {
          type: Object,
          default: () => ({
            pageSize: 10,
          }),
        },
        isLoading: {
          type: Boolean,
          default: false,
        },
        error: {
          type: Object,
          default: null,
        },
        emitReloadUsers: {
          type: Function,
          required: true,
      },
      },
      setup(props) {
        const currentPage = ref(1);
        const searchQuery = ref('');
        const filterUserType = ref('all');
        const currentSort = ref('');
        const showConfirmationBanner = ref(false);
        const selectedUserToDelete = ref<User | null>(null);
        const filteredUsers = computed(() => {
          const filteredByUsers = props.users.filter((user) => {
            // Filter by userType
            if (filterUserType.value !== 'all') {
              console.log(props.users)
              return user.role === filterUserType.value;
            }
    
            // Filter by searchQuery
            if (searchQuery.value) {
              const searchTerms = searchQuery.value.toLowerCase().split(' ');
            return searchTerms.some((term) => {
              const lowerCaseUser = Object.values(user)
                .join(' ')
                .toLowerCase();
              return lowerCaseUser.includes(term);
            });
          }

          // If both filters pass or no filters applied, include the user
          return true;
        });

        // Apply sorting
        
        return filteredByUsers;
      });

      const paginatedUsers = computed(() => {
        const start = (currentPage.value - 1) * props.paginationOptions.pageSize;
        const end = start + props.paginationOptions.pageSize;
        return filteredUsers.value.slice(start, end);
      });

      const totalUsers = computed(() => filteredUsers.value.length);
      const totalPages = computed(() => Math.ceil(totalUsers.value / props.paginationOptions.pageSize));


      const sortUsers = (sortBy: string) => {
        if (currentSort.value === sortBy) {
          currentSort.value = '-' + sortBy;
        } else {
          currentSort.value = sortBy;
        }
      };

      const prevPage = () => {
        if (currentPage.value > 1) {
          currentPage.value--;
        }
      };

      const nextPage = () => {
        if (currentPage.value < totalPages.value) {
          currentPage.value++;
        }
      };
      
      const deleteUser = (user: User) => {
        selectedUserToDelete.value = user;
        showConfirmationBanner.value = true;
      };


      const confirmDeleteUser  = async()=>{
      try {
          if (selectedUserToDelete.value) {
            await AdminService.prototype.deleteUsers(selectedUserToDelete.value.id);
            // Eliminación exitosa, ocultar el banner de confirmación
            showConfirmationBanner.value = false;
            
          }
        } catch (error) {
          alert("No se ha podido eliminar al usuario");
        }
      };

      const cancelDeleteUser = () => {
        // Limpiar el usuario seleccionado y ocultar el banner de confirmación
        selectedUserToDelete.value = null;
        showConfirmationBanner.value = false;
      };

      watch(
        showConfirmationBanner,
        async (newVal, oldVal) => {
          if (!newVal && oldVal) {

              props.emitReloadUsers();
          }
        },
        { immediate: true }
      );

      return {
        currentPage,
        searchQuery,
        filterUserType,
        currentSort,
        filteredUsers,
        totalUsers,
        totalPages,
        sortUsers,
        prevPage,
        nextPage,
        showConfirmationBanner,
        deleteUser,
        confirmDeleteUser,
        cancelDeleteUser,
        paginatedUsers
      };
    },
  });
  </script>

  <style scoped>
  .actionButton {
    border-style: none;
    background-color: transparent;
    margin-left: 30%;
    margin-top: 20%;
    color: var(--main-bg-light);
    transition: ease 0.3s;
  }
  .actionButton:hover {
    color: var(--main-bg-org);
  }

  .user-table {
    margin: 20px auto;
    width: 80%;
    border-radius: 10px;
    box-shadow: #b1b1b1 0px 10px 15px -1px, #b1b1b1 0px -10px 10px 0px;
    padding-top: 10px;
  }

  /* Wrap table in a scrollable container */
  .table-container {
    overflow-x: auto;
    -webkit-overflow-scrolling: touch;
  }

  /* Style the entire table element */
  table {
    width: 100%;
    border-collapse: collapse;
  }

  /* Style the table header */
  thead th {
    background-color: var(--main-bg-light); /* Dark blue background */
    color: #fff; /* White text */
    padding: 10px 15px; /* Add some padding */
    text-align: left;
    font-weight: bolder;
  }

  /* Style regular table cells */
  tbody th,
  tbody td {
    padding: 0px 10px;
    text-align: left;
  }

  /* Style alternating background colors for readability */
  tbody tr:nth-child(odd) {
    background-color: #fff;
  }

  tbody tr:nth-child(even) {
    background-color: #f0efef;
  }

 
  .filters {
    display: flex; 
    flex-wrap: wrap;
    gap: 10px; 
    margin-bottom: 15px;
  }

  .filters select,
  .filters input[type="text"] {
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
  }

 
  .pagination {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 20px;
  }

  .pagination button {
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
    cursor: pointer;
    margin: 0 5px; 
  }

  .pagination button:hover {
    background-color: #eee;
  }

 
  .loading,
  .error {
    text-align: center;
    margin-top: 20px;
  }

  .disabled-row {
  background-color: #f1c9cd !important;
}
  .overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5); 
    z-index: 1000;
  }

  .confirmation-banner {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
    padding: 10px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
    z-index: 1001; /* Asegúrate de que el banner esté sobre la capa de superposición */
  }

  .confirmation-banner p {
    margin: 0;
    margin-bottom: 10px;
  }

  .confirmation-banner button {
    margin-right: 10px;
    padding: 5px 10px;
    border: 1px solid #ccc;
    border-radius: 3px;
    cursor: pointer;
  }

  .confirmation-banner button:hover {
    background-color: #eee;
  }

  /* Responsive adjustments */
  @media (max-width: 768px) {
    .user-table {
      width: 95%;
    }

    .filters {
      flex-direction: column;
      align-items: stretch;
    }

    .filters select,
    .filters input[type="text"] {
      width: 100%;
      margin-bottom: 10px;
    }
  }
  </style>