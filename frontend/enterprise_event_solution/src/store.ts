import { Commit, createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate'; // Importa createPersistedState
import { authService } from './services/auth.service';
import { User } from "./models/User";

interface State {
  isAuthenticated: boolean;
  userRoles: string;
  user: User | null;
}

export default createStore({
  state: {
    isAuthenticated: false,
    userRoles: '',
    user: null,
  } as State,

  mutations: {
    setAuthentication(state: State, isAuthenticated: boolean) {
      console.log('Mutation: setAuthentication', isAuthenticated);
      state.isAuthenticated = isAuthenticated;
    },
    setUserRoles(state: State, roles: string) {
      console.log('Mutation: setUserRoles', roles);
      state.userRoles =  roles; // Add new roles to existing list
    },
    setUser(state: State, user: User | null) {
      state.user = user;
    },
    updateUser(state: State, newUser:User) {
      console.log('Mutation: updateUser', newUser);
      state.user = newUser;
    }
  },
  getters: {
    // Esta getter verifica si el usuario tiene los roles requeridos
    hasRequiredRoles: (state) => (requiredRoles: string[]) => {
      console.log('requiredRoles:', requiredRoles);
      console.log('state.userRoles:', state.userRoles);
      if (state.userRoles) {
        return requiredRoles.includes(state.userRoles);
      }
    },
  },
  actions: {
    async login({ commit }: { commit: Commit }, user: User) {
      try {
        const loginAccepted = await authService.prototype.login(user);
        const  userData = await authService.prototype.getUserInfoFromServer();
        console.log(userData);
        if (!loginAccepted || !userData) throw new Error("Error en la autenticación");
        commit('setAuthentication', true);
        commit('setUserRoles',userData.role);
        commit('setUser',userData);
        // Almacenar el token JWT si es necesario
        // localStorage.setItem('jwtToken', userData.token);
      } catch (error) {
        console.error('Error al realizar el login:', error);
        throw error;
      }
    },
    logout({ commit }: { commit: Commit }) {
      try{
      // Lógica de cierre de sesión
      commit('setAuthentication', false);
      commit('setUserRoles', '');
      commit('setUser', null);
      
      // Limpiar el token JWT si lo estás utilizando
      // localStorage.removeItem('jwtToken');
    }catch (error) {
      console.error('Error al realizar el login:', error);
      throw error;
    }
    },


    updateUser({ commit }, newUser) {
      commit('updateUser', newUser);
    }
  },
  plugins: [createPersistedState()] // Agrega el plugin createPersistedState
});
