import {createApp} from 'vue'
import './style.scss'
import App from './App.vue'
import {createRouter, createWebHashHistory} from "vue-router";
import {isSystemDarkTheme} from "./Global.ts";


const app = createApp(App)
app.use(createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: '/',
            component: () => import('@pages/Home.vue')
        }
    ]
}))
app.mount(document.body)

if (isSystemDarkTheme()) {
    document.documentElement.classList.add("dark")
}
