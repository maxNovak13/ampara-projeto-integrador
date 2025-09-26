<template>
  <div class="max-w-5xl border border-gray-300 mx-auto mt-5 mb-5 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center text-purple-800">Cadastrar Agressor(es)</h1>

    <!-- Formulário para adicionar novo agressor -->
    <form @submit.prevent="adicionarAgressor" class="grid grid-cols-1 md:grid-cols-2 gap-6">

      <!-- Coluna esquerda -->
      <div class="flex flex-col gap-6">
        <div>
          <label class="text-gray-700 font-medium">Nome</label>
          <input
              v-model="novoAgressor.nome"
              maxlength="100"
              class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>

        <div>
          <label class="text-gray-700 font-medium">Apelido</label>
          <input
              v-model="novoAgressor.apelido"
              maxlength="50"
              class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
          />
        </div>
        <div class="flex gap-4">
          <div class="w-1/2">
            <label class="text-gray-700 font-medium">CPF</label>
            <input
                v-model="novoAgressor.cpf"
                v-mask="'###.###.###-##'"
                maxlength="14"
                placeholder="000.000.000-00"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
          </div>
          <div class="w-1/2">
            <label class="text-gray-700 font-medium">Telefone</label>
            <input
                v-model="novoAgressor.telefone"
                v-mask="'(##) #####-####'"
                maxlength="15"
                placeholder="(00) 00000-0000)"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
          </div>
        </div>
      </div>

      <!-- Coluna direita -->
      <div class="flex flex-col gap-6">
        <div>
          <label class="text-gray-700 font-medium">Descrição / Observações</label>
          <textarea
              v-model="novoAgressor.texto"
              maxlength="300"
              class="border border-gray-300 rounded px-5 py-3 h-19 resize-none w-full focus:outline-none focus:ring-2 focus:ring-purple-500">

          </textarea>
        </div>
        <div>
          <label class="text-gray-700 font-medium">Endereço</label>
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <input
                v-model="novoAgressor.endereco.rua"
                placeholder="Rua"
                maxlength="100"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <input
                v-model="novoAgressor.endereco.numero"
                placeholder="Número"
                maxlength="15"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <input
                v-model="novoAgressor.endereco.bairro"
                placeholder="Bairro"
                maxlength="80"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <input
                v-model="novoAgressor.endereco.complemento"
                placeholder="Complemento"
                maxlength="100"
                class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
          </div>
        </div>
      </div>

      <div class="col-span-1 md:col-span-2 bg-yellow-100 border-l-4 border-yellow-500 text-yellow-800 p-4 rounded" role="alert">
        <p class="font-semibold">Atenção:</p>
        <p>Adicione ao menos um agressor para que ele seja cadastrado junto ao registro.</p>
      </div>

      <!-- Botão Adicionar -->
      <div class="col-span-1 md:col-span-2 flex justify-end">
        <button type="submit" class="bg-purple-300 text-gray-800 py-2 px-7 mt-3 rounded hover:bg-purple-400 transition">
          Adicionar agressor</button>
      </div>
    </form>

    <ListaAgressores
        :agressores="registroStore.registro.agressores"
        :exibirBotaoExcluir="true"
        @remover="registroStore.registro.agressores.splice($event, 1)"
    />

    <!-- Botão Finalizar -->
    <div class="flex justify-between mt-6">
      <BotaoVoltar></BotaoVoltar>
      <button @click="finalizarCadastro" class="bg-purple-600 text-white py-2 px-6 mt-2 rounded hover:bg-purple-700 transition">Finalizar Cadastro</button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRegistroStore } from '../../stores/registroStore.js'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import BotaoVoltar from "../../components/BotaoVoltar.vue";
import ListaAgressores from '../../components/ListaAgressores.vue'
import { useUserStore } from '../../stores/user.js'

const userStore = useUserStore()
const token = userStore.token

const router = useRouter()
const toast = useToast()
const registroStore = useRegistroStore()
const registro = registroStore.registro

const novoAgressor = reactive({
  nome: '',
  apelido: '',
  telefone: '',
  cpf: '',
  texto: '',
  endereco: {
    rua: '',
    numero: '',
    bairro: '',
    complemento: ''
  }
})

//aplica lógica para adicionar agressor -> pelo menos UM valor preenchido
function adicionarAgressor() {
  const algumCampoPreenchido =
      novoAgressor.nome.trim() ||
      novoAgressor.apelido.trim() ||
      novoAgressor.telefone.trim() ||
      novoAgressor.cpf.trim() ||
      novoAgressor.texto.trim() ||
      novoAgressor.endereco.rua.trim() ||
      novoAgressor.endereco.numero.trim() ||
      novoAgressor.endereco.bairro.trim() ||
      novoAgressor.endereco.complemento.trim()

  if (!algumCampoPreenchido) {
    toast.warning('Preencha ao menos um campo antes de adicionar o agressor.')
    return
  }

  const copia = JSON.parse(JSON.stringify(novoAgressor))
  registro.agressores.push(copia)

  // Resetar campos do agressor
  Object.assign(novoAgressor, {
    nome: '',
    apelido: '',
    telefone: '',
    cpf: '',
    texto: '',
    endereco: { rua: '', numero: ', ', bairro: '- ', complemento: '' }
  })
}

//cadastra com agressor
async function finalizarCadastro() {
  if (registro.agressores.length === 0) {
    toast.error('Adicione pelo menos um agressor antes de finalizar.')
    return
  }

  try {
    await registroStore.enviar()
    toast.success('Registro cadastrado com sucesso!')
    registroStore.resetar()
    await router.push(`/perfil-mulher/${registro.mulher.uuid}`)
  } catch (e) {
    toast.error('Erro ao cadastrar agressor. Verifique os campos e tente novamente.')
  }
}
</script>

<style scoped>
</style>
