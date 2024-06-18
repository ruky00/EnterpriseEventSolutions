import { NavigationGuardNext, RouteLocationNormalized } from 'vue-router';
import store from '../store'; 

export const authGuard = async (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext) => 
  {
  const isAuthenticated = store.state.isAuthenticated;
  const requiredRoles = (to.meta.roles as string[]) || [];

  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login');
  } else if (requiredRoles.length > 0 && !store.getters.hasRequiredRoles(requiredRoles)) {
    next('/unauthorized');
  } else {
    next();
  }

};
