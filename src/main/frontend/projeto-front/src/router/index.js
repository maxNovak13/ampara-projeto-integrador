import { createRouter, createWebHistory } from 'vue-router';

import Login from '../pages/profissional/Login.vue';
import Cadastro from '../pages/profissional/Cadastro.vue';
import Inicio from '../pages/Inicio.vue';
import CadastrarMulher from '../pages/mulher/CadastrarMulher.vue';
import PerfilMulher from "../pages/mulher/PerfilMulher.vue";
import BuscarMulher from '../pages/mulher/BuscarMulher.vue';
import GerenciarLocal from '../pages/local/GerenciarLocal.vue';
import CadastrarLocal from '../pages/local/CadastrarLocal.vue';
import GerenciarProfissional from '../pages/profissional/GerenciarProfissional.vue';
import CadastrarRegistro from "../pages/registro/CadastrarRegistro.vue";
import CadastrarAgressor from "../pages/registro/CadastrarAgressor.vue"




const routes = [
    {
        path: '/',
        redirect: '/login',
    },
    {
        path: '/login',
        component: Login,
    },
    {
        path: '/cadastro',
        component: Cadastro,
    },
    {
        path: '/inicio',
        component: Inicio,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/cadastrar-mulher',
        component: CadastrarMulher,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/perfil-mulher/:uuid',
        name: 'PerfilMulher',
        component: () => import('../pages/mulher/PerfilMulher.vue'),
        meta: { requiresAuth: true },// rota protegida
    },
    {
        path: '/buscar-mulher',
        component: BuscarMulher,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/gerenciar-local',
        component:  GerenciarLocal,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/cadastrar-local',
        component:  CadastrarLocal,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/gerenciar-profissional',
        component: GerenciarProfissional,
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/registro/:uuid',
        name: 'VisualizarRegistro',
        component: () => import('../pages/registro/VisualizarRegistro.vue'),
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/cadastrar-registro/:uuidMulher',
        name: 'CadastrarRegistro',
        component: () => import('../pages/registro/CadastrarRegistro.vue'),
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/cadastrar-agressor',
        component: CadastrarAgressor,
        meta: { requiresAuth: true }, // rota protegida
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Proteção das rotas
import { useUserStore } from '../stores/user';
import { storeToRefs } from 'pinia';

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const { userData } = storeToRefs(userStore);
    const token = localStorage.getItem('token');

    if (token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const now = Date.now() / 1000;

            if (payload.exp < now) {
                //console.warn('Token expirado.');
                userStore.logout();
                return next('/login');
            }

            if (!userData.value) {
                await userStore.fetchUserProfile();
            }

            // se já esta logado redireciona pra home
            if (to.path === '/login') {
                return next('/inicio');
            }

        } catch (error) {
            //console.error('Erro ao processar token ou buscar perfil:', error);
            userStore.logout();
            return next('/login');
        }
    }

    if (to.meta?.requiresAuth) {
        if (!token || userData.value?.situacao !== 'ATIVO') {
            alert('Você não tem permissão para acessar o sistema.');
            return next('/login');
        }
    }

    next();
});

export default router;