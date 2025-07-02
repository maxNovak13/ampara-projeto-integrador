import { createRouter, createWebHistory } from 'vue-router';

import Login from '../pages/Login.vue';
import Cadastro from '../pages/Cadastro.vue';
import Inicio from '../pages/Inicio.vue';
import CadastrarMulher from '../pages/CadastrarMulher.vue';
import PerfilMulher from "../pages/PerfilMulher.vue";
import BuscarMulher from '../pages/BuscarMulher.vue';
import GerenciarLocal from '../pages/GerenciarLocal.vue';
import CadastrarLocal from '../pages/CadastrarLocal.vue';
import GerenciarProfissional from '../pages/GerenciarProfissional.vue';
import CadastrarRegistro from "../pages/CadastrarRegistro.vue";
import CadastrarAgressor from "../pages/CadastrarAgressor.vue"
import { useUserStore } from '../stores/user';
import { storeToRefs } from 'pinia';



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
        component: () => import('../pages/PerfilMulher.vue'),
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
        component: () => import('../pages/VisualizarRegistro.vue'),
        meta: { requiresAuth: true }, // rota protegida
    },
    {
        path: '/cadastrar-registro/:uuidMulher',
        name: 'CadastrarRegistro',
        component: () => import('../pages/CadastrarRegistro.vue'),
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
router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const { userData } = storeToRefs(userStore);

    const token = localStorage.getItem('token');

    // Se tem token mas o perfil ainda não foi carregado
    if (token) {
        try {
            const payload = JSON.parse(atob(token.split('.')[1]));
            const now = Date.now() / 1000;

            if (payload.exp < now) { //verifica se o token esta expirado
                console.warn('Token expirado.');
                userStore.logout();
                return next('/login');
            }

            if (!userData.value) {//busca perfil
                await userStore.fetchUserProfile(payload.uuid);
            }

        } catch (error) {
            console.error('Erro ao processar token ou buscar perfil:', error);
            userStore.logout();
            return next('/login');
        }
    }

    // Proteção com segurança contra dados nulos
    const situacao = userData.value?.situacao;

    if (to.meta?.requiresAuth) {
        if (!token || situacao !== 'ATIVO') {
            alert('Você não tem permissão para acessar o sistema.');
            return next('/login');
        }
    }

    next();
});

export default router;
