import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from './pages/HomePage'
import AuthPage from "./pages/AuthPage";


Vue.use(VueRouter);

export const router = new VueRouter({
    mode: 'history',
    routes: [
        {path: '/', component: HomePage},
        {path: '/auth', component: AuthPage},
        {path: '*', redirect: '/'}
    ]
});

router.beforeEach((to, from, next) => {
    const publicPage = ['/auth', '/register'];
    const authRequired = !publicPage.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        return next('/auth')
    }else {
        next();
    }
});

