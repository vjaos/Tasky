import Vue from 'vue';
import App from './App';
import 'vuetify/dist/vuetify.min.css';
import vuetify from "./plugins/vuetify";
import store from "./store";
import {router} from "./router";
import vueKanban from 'vue-kanban'



Vue.config.productionTip = false;

Vue.use(vueKanban)
new Vue({
    vuetify,
    router,
    store,
    render: h => h(App),
}).$mount('#app')
