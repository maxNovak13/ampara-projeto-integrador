<template>
  <div class="max-w-lg mx-auto border border-gray-300 mt-5 mb-5 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center">Cadastrar conta</h1>

    <!-- Formulário de cadastro -->
    <form @submit.prevent="handleCadastro" class="flex flex-col gap-6">
      <div class="flex flex-col">
        <label for="nome" class="mb-1 text-gray-700 font-medium">Nome completo</label>
        <input
            id="nome"
            v-model="nome"
            type="text"
            maxlength="100"
            placeholder="Insira seu nome completo"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div class="flex flex-col">
        <label for="email" class="mb-1 text-gray-700 font-medium">E-mail</label>
        <input
            id="email"
            v-model="email"
            type="email"
            maxlength="150"
            placeholder="Insira seu e-mail"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div class="flex flex-col">
        <label for="profissao" class="mb-1 text-gray-700 font-medium">Profissão</label>
        <input
            id="profissao"
            v-model="profissao"
            type="text"
            placeholder="Insira sua profissão"
            maxlength="25"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div class="flex flex-col">
        <label for="registro" class="mb-1 text-gray-700 font-medium">Registro Profissional</label>
        <input
            id="registro"
            v-model="registro"
            type="text"
            placeholder="Insira seu registro profissional"
            maxlength="20"
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
            maxlength="30"
            placeholder="Insira sua senha"
            required
            class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <p class="text-sm text-center text-gray-600">
        Já tem conta?
        <router-link to="/login" class="text-blue-600 hover:underline">
          Acesse por aqui
        </router-link>
      </p>

      <div class="flex justify-between">
        <BotaoCancelar destino="/login"/>

        <button
            type="submit"
            class="bg-purple-700 text-white py-3 px-6 rounded hover:bg-purple-800 transition"
        >
          Cadastrar
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useRouter } from 'vue-router';
import BotaoCancelar from "../components/BotaoCancelar.vue";

const nome = ref('');
const email = ref('');
const profissao = ref('');
const registro = ref('');
const senha = ref('');

const errorMsg = ref('');
const router = useRouter();

async function handleCadastro() {
  errorMsg.value = '';

  try {
    const profissional = {
      nome: nome.value,
      email: email.value,
      profissao: profissao.value,
      registro: registro.value,
      senha: senha.value,
    };

    const response = await axios.post('http://localhost:8080/ampara/profissional', profissional);

    if (response.status === 201) {
      alert('Cadastro realizado com sucesso! Espere receber a permissão de acesso de um administrador.');
      router.push('/login'); // redireciono para login após cadastro
    }

  } catch (error) {
    if (error.response) {
      if (error.response.status === 400) {
        errorMsg.value = 'Dados inválidos fornecidos. Verifique os campos.';
      } else {
        errorMsg.value = `Erro ${error.response.status}: ${error.response.data?.message || 'Erro desconhecido'}`;
      }
    } else {
      errorMsg.value = 'Erro na conexão. Verifique sua rede.';
    }
  }
}
</script>

<style>

</style>

