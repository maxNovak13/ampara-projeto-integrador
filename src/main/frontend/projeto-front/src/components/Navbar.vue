<template>
  <div>
    <!-- Barra superior -->
    <nav class="flex justify-between items-center p-4 bg-purple-300">
      <div class="flex items-center gap-2">
        <img src="../assets/logo-icon.png" alt="Logo" class="h-8 w-8" />
        <span class="font-bold text-xl">Ampara</span>
      </div>

      <!-- Botão muda conforme nome do usuário logado-->
      <div v-if="profissional">
        <div class="relative" ref="dropdownRef">
          <button
              @click="toggleDropdown"
              class="bg-purple-300 text-black font-semibold px-4 py-2 rounded flex items-center gap-2"
          >
            <img src="../assets/user-icon.png"  alt="Avatar" class="w-6 h-6 rounded-full" />
             {{ profissional.nome || 'Usuário' }} &nbsp
            <img src="../assets/seta-icon.png"  alt="Seta para baixo" class="w-4 h-4 rounded-full" />
          </button>

          <div
              v-if="dropdownOpen"
              class="absolute right-0 mt-2 w-32 bg-white rounded shadow-lg text-gray-700"
          >
            <button
                @click="handleLogout"
                class="w-full text-left px-4 py-2 hover:bg-purple-100"
            >
              Sair
            </button>
          </div>
        </div>
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
          v-if="profissional?.adm"
          to="/gerenciar-profissional"
          active-class="text-purple-700 border-b-2 border-purple-700"
      >
        Gerenciar profissionais
      </router-link>
      <router-link
          v-if="profissional?.adm"
          to="/gerenciar-local"
          active-class="text-purple-700 border-b-2 border-purple-700"
      >
        Gerenciar locais
      </router-link>
    </nav>
  </div>
</template>

<script setup>
import { ref, onBeforeUnmount } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuth } from '../composables/auth.js'; // ajuste o caminho conforme necessário

const router = useRouter();
const route = useRoute();
const dropdownOpen = ref(false);
const dropdownRef = ref(null);

const { profissional, logout } = useAuth();

function handleClick() {//lógica para aparecer ou não o botão Acessar/Cadastrar-se
  if (route.path === '/login') {
    router.push('/cadastro');
  } else {
    router.push('/login');
  }
}

function toggleDropdown() {//lógica do botão dropdown do usuário nome->Sair
  dropdownOpen.value = !dropdownOpen.value;

  if (dropdownOpen.value) {
    window.addEventListener('click', handleClickOutside);
  } else {
    window.removeEventListener('click', handleClickOutside);
  }
}

function handleClickOutside(event) {//fecha dropdown se usuário clicar fora dele
  if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
    dropdownOpen.value = false;
    window.removeEventListener('click', handleClickOutside);
  }
}

function handleLogout() {//implementa o sair da conta = limpa localStorage e atualiza os refs
  logout(); //
  router.push('/login');
}

onBeforeUnmount(() => {//remove o listener
  window.removeEventListener('click', handleClickOutside);
});
</script>