import { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import { useStore } from 'vuex';

export const authGuard = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext) => 
  {
  const store = useStore();
  const isAuthenticated = store.state.isAuthenticated;
  const requiredRoles = (to.meta.roles as string[]) || [];

  // Asegúrate de que la acción de inicio de sesión se complete antes de verificar isAuthenticated


  if (to.meta.requiresAuth && !isAuthenticated) {
    // Redirige a la página de inicio de sesión si no está autenticado
    next('/');
  } else if (requiredRoles.length > 0 && !store.getters.hasRequiredRoles(requiredRoles)) {
    // Redirige a una página no autorizada si no tiene los roles requeridos
    next('/unauthorized');
  } else {
    // Permite el acceso a la ruta
    next();
  }

};