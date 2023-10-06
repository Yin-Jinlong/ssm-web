import {createApp} from 'vue'
import './style.scss'
import App from './App.vue'
import {createRouter, createWebHashHistory} from "vue-router";


const app = createApp(App)
app.use(createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: () => import('@pages/home')
        }, {
            path: '/:pathMatch(.*)',
            component: () => import('@pages/not-found')
        }
    ]
}))
app.mount(document.body)