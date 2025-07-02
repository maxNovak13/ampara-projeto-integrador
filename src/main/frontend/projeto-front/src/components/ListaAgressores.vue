<template>
  <div v-if="agressores?.length">
    <h2 class="text-2xl font-bold text-purple-800 mb-6 text-center">Agressores Adicionados</h2>

    <div
        v-for="(agressor, index) in agressores"
        :key="index"
        class="bg-white border border-gray-300 rounded-2xl p-6 mb-6"
    >
      <!-- Cabeçalho -->
      <div class="flex justify-between items-center mb-4">
        <h3 class="text-xl font-semibold text-purple-800">Agressor {{ index + 1 }}</h3>
        <!-- Botão excluir, condicional -->
        <button
            v-if="exibirBotaoExcluir"
            @click="removerAgressor(index)"
            class="bg-red-800 text-white px-3 py-1 rounded hover:bg-red-900 transition"
        >
          Remover
        </button>
      </div>

      <!-- Linha 1: Nome, Apelido, CPF, Telefone -->
      <div class="grid grid-cols-1 md:grid-cols-4 gap-4 mb-4">
        <div class="text-base">
          <p class="text-black font-medium">Nome</p>
          <p class="text-gray-800">{{ agressor.nome }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Apelido</p>
          <p class="text-gray-800">{{ agressor.apelido || '-' }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">CPF</p>
          <p class="text-gray-800">{{ agressor.cpf }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Telefone</p>
          <p class="text-gray-800">{{ agressor.telefone }}</p>
        </div>
      </div>

      <!-- Linha 2: Endereço e Descrição -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div class="text-base">
          <p class="text-black font-medium">Endereço</p>
          <p class="text-gray-800">
            {{ agressor.endereco.rua }}, {{ agressor.endereco.numero }} -
            {{ agressor.endereco.bairro }},
            {{ agressor.endereco.complemento || '-' }}
          </p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Descrição</p>
          <p class="text-gray-800">{{ agressor.texto }}</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { defineProps, defineEmits } from 'vue'


const props = defineProps({
  agressores: {
    type: Array,
    required: false,
    default: () => []
  },
  exibirBotaoExcluir: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['remover'])

function removerAgressor(index) {
  emit('remover', index)
}

</script>