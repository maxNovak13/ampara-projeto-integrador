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
        <p v-if="errorEmail" class="text-red-600 text-sm mt-1">{{ errorEmail }}</p>
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
        <p v-if="errorSenha" class="text-red-600 text-sm mt-1">{{ errorSenha }}</p>
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
  import { login } from '../composables/auth.js';

  const email = ref('');
  const senha = ref('');
  const errorEmail = ref('');
  const errorSenha = ref('');
  const errorAcesso = ref('');
  const router = useRouter();

  async function handleLogin() {
    errorEmail.value = '';
    errorSenha.value = '';

    try {
      const response = await axios.post('http://localhost:8080/ampara/login', {
        email: email.value,
        senha: senha.value,
      });

      console.log('Usuário autenticado:', response.data);
      //console.log('Usuário autenticado SITUACAO:', response.data.situacao);

      login(response.data) // Define os dados do profissonal que foi autenticado no sistema na sessão
      await router.push('/inicio');

    } catch (error) {
      if (error.response && error.response.data) {
        const message = error.response.data.message || '';

        if (message.toLowerCase().includes('senha')) {
          errorSenha.value = message;
        } else if (message.toLowerCase().includes('profissional') || message.toLowerCase().includes('não encontrado')) {
          errorEmail.value = message;
        }  else if (message.toLowerCase().includes('acesso negado')){
          errorAcesso.value = message;
        }
        else {
          // erro genérico para ambos
          errorEmail.value = 'Email ou senha inválidos.';
          errorSenha.value = 'Email ou senha inválidos.';
        }
      } else {
        errorEmail.value = 'Erro na conexão. Verifique sua rede.';
      }
    }
  }
</script>