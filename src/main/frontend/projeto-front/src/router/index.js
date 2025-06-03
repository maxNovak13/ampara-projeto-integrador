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
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

// Guard global para proteger rotas que exigem autenticação e situação ATIVO
router.beforeEach((to, from, next) => {
    const profissional = JSON.parse(localStorage.getItem('profissional'));

    if (to.meta.requiresAuth) {
        if (!profissional || profissional.situacao !== 'ATIVO') {
            alert('Você não tem permissão para acessar esta página.');
            return next('/login');
        }
    }

    next();
});

export default router;
