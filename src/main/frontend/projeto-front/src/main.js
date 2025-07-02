import { createApp } from 'vue'
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css"
import './style.css'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router';
import VueTheMask from 'vue-the-mask'

const pinia = createPinia()
const options = {
    position: 'top-right',
    timeout: 5000,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    hideProgressBar: false,
}


createApp(App).
use(router).
use(pinia).
use(VueTheMask).
use(Toast, options).
mount('#app');
