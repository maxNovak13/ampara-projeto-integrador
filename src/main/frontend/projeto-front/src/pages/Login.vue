<template>
  <div class="max-w-lg mx-auto border border-gray-300 mt-20 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center">Acessar conta</h1>
    <div class="text-center">
      <p v-if="errorAcesso" class="text-red-600 bg-red-100 rounded px-3 py-2 text-sm mt-1">{{ errorAcesso }}</p> <br/>
    </div>

    <!-- Formulário de login -->
    <form @submit.prevent="handleLogin" class="flex flex-col gap-6">
      <div class="flex flex-col">
        <label for="email" class="mb-1 text-gray-700 font-medium">Email</label>
        <input
            id="email"
            v-model="email"
            type="email"
            placeholder="Insira seu email"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div class="flex flex-col">
        <label for="senha" class="mb-1 text-gray-700 font-medium">Senha</label>
        <input
            id="senha"
            v-model="senha"
            type="password"
            placeholder="Insira sua senha"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <p v-if="errorValue" class="text-red-600 text-sm mt-1">{{ errorValue }}</p>
      </div>

      <p class="text-sm text-center text-gray-600">
        Já tem login?
        <router-link to="/cadastro" class="text-blue-600 hover:underline">
          Cadastre-se aqui
        </router-link>
      </p>

      <button
          type="submit"
          class="bg-purple-700 text-white py-3 rounded hover:bg-blue-900 transition"
      >
        Entrar
      </button>
    </form>
  </div>
</template>

<script setup>
  import axios from 'axios';
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { useUserStore } from '../stores/user';

  const userStore = useUserStore();
  const email = ref('');
  const senha = ref('');
  const errorValue = ref('');
  const errorAcesso = ref('');
  const router = useRouter();

  async function handleLogin() {
    errorValue.value = '';
    errorAcesso.value = '';

    try {
      const response = await axios.post('http://localhost:8080/ampara/login', {
        email: email.value,
        senha: senha.value,
      });

      // Chama login da store e aguarda
      await userStore.login(response.data.token);

      await router.push('/inicio');

    } catch (error) {
      const message = error?.message || '';

      if (message.includes('não ativo')) {
        errorAcesso.value = 'Seu usuário não está ativo. Contate o administrador.';
      } else if (error.response && error.response.status === 400) {
        // Se o status for 400, assume erro de credenciais
        errorValue.value = 'Email ou senha incorretos.';
      } else if (error.response && error.response.data) {
        const msg = error.response.data.message.toLowerCase();

        if (msg.includes('senha')) {
          errorSenha.value = error.response.data.message;
        } else if (msg.includes('profissional') || msg.includes('não encontrado')) {
          errorEmail.value = error.response.data.message;
        } else if (msg.includes('acesso negado')) {
          errorAcesso.value = error.response.data.message;
        }
      } else {
        errorValue.value = 'Erro na conexão. Verifique sua rede.';
      }
    }
  }
</script>