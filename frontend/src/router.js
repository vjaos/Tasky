import Vue from 'vue'
import VueRouter from 'vue-router'

import LoginView from "./view/LoginView";
import DashboardView from "./view/DashboardView";
import ProjectView from "./view/ProjectView";
import EventsView from "./view/EventsView";
import BoardView from "./view/BoardView";
import SettingsView from "./view/SettingsView";


Vue.use(VueRouter);

export const router = new VueRouter({
    mode: 'history',
    routes: [
        {path: '/', component: DashboardView},
        {path: '/login', component: LoginView},
        {path: '/projects', component: ProjectView},
        {path: '/events', component: EventsView},
        {path: '/board', component: BoardView},
        {path: '/settings', component: SettingsView},
        {path: '*', redirect: '/'}
    ]
});

router.beforeEach((to, from, next) => {
    const publicPage = ['/login', '/', '/register'];
    const authRequired = !publicPage.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        return next('/login')
    } else {
        next();
    }
});

