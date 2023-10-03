import {createApp} from 'vue'
import './style.scss'
import App from './App.vue'
import {createRouter, createWebHashHistory} from "vue-router";
import {getTheme} from "Global";


const app = createApp(App)
app.use(createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: () => import('@pages/Home.vue')
        }, {
            path: '/:pathMatch(.*)',
            component: () => import('@pages/NotFoundPage.vue')
        }
    ]
}))
app.mount(document.body)

if (getTheme())
    document.documentElement.classList.add("dark")