<template>
  <div>
    <!-- Barra superior -->
    <nav class="flex justify-between items-center p-4 bg-purple-300">
      <div class="flex items-center gap-2">
        <img src="../assets/logo-icon.png" alt="Logo" class="h-8 w-8" />
        <span class="font-bold text-xl">Ampara</span>
      </div>

      <!-- Botão logout com nome e ícone Sair-->
      <div v-if="profissional" class="flex items-center gap-2">
        <img src="../assets/user-icon.png" alt="Avatar" class="w-6 h-6 rounded-full" />
        <span class="font-semibold text-black">{{ profissional.nome || 'Usuário' }}</span>
        <button @click="handleLogout" class="ml-2">
          <img src="../assets/sair-icon.png" alt="Sair" class="w-5 h-5" />
        </button>
      </div>

      <!-- Visitante: botão login/cadastro -->
      <div v-else>
        <button
            @click="handleClick"
            class="bg-purple-500 text-white px-4 py-2 rounded hover:bg-purple-700 transition"
        >
          {{ route.path === '/login' ? 'Cadastrar-se' : 'Acessar' }}
        </button>
      </div>
    </nav>

    <!-- Barra inferior visível só para usuários logados -->
    <nav
        v-if="profissional"
        class="flex gap-6 px-6 py-3 bg-purple-200 text-purple-900 font-semibold"
    >
      <router-link to="/inicio" active-class="text-purple-700 border-b-2 border-purple-700">
        Início
      </router-link>
      <router-link to="/cadastrar-mulher" active-class="text-purple-700 border-b-2 border-purple-700">
        Cadastrar mulher
      </router-link>
      <router-link to="/buscar-mulher" active-class="text-purple-700 border-b-2 border-purple-700">
        Buscar mulher
      </router-link>
      <!-- Apenas administradores podem acessar e visualizar  -->
      <router-link
          v-if="profissional.role === 'ADMIN'"
          to="/gerenciar-profissional"
          active-class="text-purple-700 border-b-2 border-purple-700"
      >
        Gerenciar profissionais
      </router-link>
      <router-link
          v-if="profissional.role === 'ADMIN'"
          to="/gerenciar-local"
          active-class="text-purple-700 border-b-2 border-purple-700"
      >
        Gerenciar locais
      </router-link>
    </nav>
  </div>
</template>

<script setup>
import {ref, onBeforeUnmount, onMounted, computed} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from '../stores/user'

const router = useRouter();
const route = useRoute();
const dropdownOpen = ref(false);
const dropdownRef = ref(null);


const userStore = useUserStore()

onMounted(() => {
  const uuid = localStorage.getItem('uuid');
  if (uuid) userStore.fetchUserProfile(uuid);
});

const profissional = computed(() => userStore.userData)

function handleClick() {//lógica para aparecer ou não o botão Acessar/Cadastrar-se
  if (route.path === '/login') {
    router.push('/cadastro');
  } else {
    router.push('/login');
  }
}

function handleLogout() {//implementa o sair da conta
  userStore.logout();
  router.push('/login');
}
</script>