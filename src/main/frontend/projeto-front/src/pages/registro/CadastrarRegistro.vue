<template>
  <div class="max-w-5xl border border-gray-300 mx-auto mt-5 mb-5 p-8 bg-white rounded shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center text-purple-800">Cadastrar Registro</h1>

    <form @submit.prevent="continuar" class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Lado esquerdo -->
      <div class="flex flex-col gap-6">
        <div>
          <label class="mb-1 text-gray-700 font-medium">Data do Ocorrido</label>
          <input
              v-model="registro.dataOcorrido"
              type="date"
              required
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Tipo de Violência</label>
          <input
              v-model="registro.tipoViolencia"
              type="text"
              required
              maxlength="200"
              placeholder="Ex.: Psicológica e física"
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Relação com a Vítima</label>
          <select v-model="registro.relacaoVitima" required class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full">
            <option disabled value="">Selecione</option>
            <option>Conhecido</option>
            <option>Desconhecido</option>
            <option>Amigo</option>
            <option>Namorado</option>
            <option>Marido</option>
            <option>Ex-Companheiro</option>
            <option>Outro</option>
          </select>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Lugar do Ocorrido</label>
          <input
              v-model="registro.lugar"
              type="text"
              required
              maxlength="100"
              placeholder="Ex.: Na frente da casa da vítima."
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full" />
        </div>

        <!-- Checkbox -->
        <div>
          <label class="mb-1 text-gray-700 font-medium">Houve violência física?</label>
          <select
              v-model="registro.violencia"
              required
              class="border border-gray-300 rounded px-5 py-3 w-full focus:outline-none focus:ring-2 focus:ring-purple-500"
          >
            <option disabled value="">Selecione</option>
            <option :value="true">Sim</option>
            <option :value="false">Não</option>
          </select>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Depoimento da vítima:</label>
          <textarea
              v-model="registro.depoimento"
              required
              maxlength="400"
              placeholder="Ex.: A vítima alega ter sido ameaçada pelo ex-companheiro."
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full h-24 resize-none"></textarea>
        </div>
      </div>

      <!-- Lado direito -->
      <div class="flex flex-col gap-6">
        <div>
          <label class="mb-1 text-gray-700 font-medium">Local do Atendimento</label>
          <input
              v-model="localSelecionadoNome"
              @input="buscarLocais"
              list="locais-opcoes"
              placeholder="Digite para buscar e selecione o local"
              class="border border-gray-300 rounded px-5 py-3 w-full" />

          <datalist id="locais-opcoes">
            <option v-for="local in locais" :key="local.id" :value="local.nome" />
          </datalist>

          <p v-if="erroLocal" class="text-red-600 mt-1">{{ erroLocal }}</p>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Descrição do profissional sobre o caso:</label>
          <textarea
              v-model="registro.descricao"
              required
              maxlength="400"
              placeholder="Ex.: Vítima relata agressão por companheiro, apresenta machucados e está emocionalmente abalada."
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full h-32 resize-none"></textarea>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Conduta do profissional:</label>
          <textarea
              v-model="registro.conduta"
              required
              maxlength="400"
              placeholder="Ex.: Realizado acolhimento, avaliação inicial e orientação sobre a rede de apoio e medidas legais."
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full h-32 resize-none"></textarea>
        </div>

        <div>
          <label class="mb-1 text-gray-700 font-medium">Encaminhamento dado a vítima sobre o caso:</label>
          <textarea
              v-model="registro.encaminhamento"
              required
              maxlength="200"
              placeholder="Ex.: Encaminhada à delegacia."
              class="border border-gray-300 rounded px-5 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 w-full h-32 resize-none"></textarea>
        </div>
      </div>

    </form>

    <!-- Botões -->
    <div class="col-span-1 md:col-span-2 flex justify-between">
      <BotaoVoltar/>

      <button
          type="button"
          @click="continuar"
          class="bg-purple-600 text-white py-2 px-6 mt-3 rounded hover:bg-purple-800 transition flex items-center gap-2">
        Continuar
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
import { useRegistroStore } from '../../stores/registroStore.js'
import BotaoVoltar from "../../components/BotaoVoltar.vue"
import api from '../../services/api.js'
import { useUserStore } from '../../stores/user.js'


const userStore = useUserStore()
const profissional = userStore.userData
const token = userStore.token

const toast = useToast()
const registroStore = useRegistroStore()
const registro = registroStore.registro

const route = useRoute()
const router = useRouter()
const uuidMulher = route.params.uuidMulher

const locais = ref([])
const localSelecionadoNome = ref('')
const erroLocal = ref('')

// Atualiza localId no registro quando nome do local muda
watch(localSelecionadoNome, (novoNome) => {
  const localEncontrado = locais.value.find(l => l.nome === novoNome)
  registro.localId = localEncontrado?.id || null
})

// Buscar locais
async function buscarLocais() {
  if (localSelecionadoNome.value.length >= 3) {
    try {
      const response = await api.get('/local/buscar', {
        params: { nome: localSelecionadoNome.value }
      })
      locais.value = response.data
    } catch (error) {
      //console.error('Erro ao buscar locais:', error)
      toast.error('Erro ao buscar locais')
    }
  }
}

//cadastra se não tiver violencia = true
async function continuar() {
  if (!registro.localId) {
    erroLocal.value = 'Você deve selecionar um local válido da lista'
    return
  }
  erroLocal.value = ''

  registroStore.setDadosIniciais({
    ...registro,
    local: { id: registro.localId },
    mulher: { uuid: uuidMulher },
    profissional: { uuid: profissional?.uuid }
  })

  if (registro.violencia) {
    await router.push('/cadastrar-agressor')
  } else {
    try {
      await registroStore.enviar() // passe o token para o envio
      toast.success('Registro cadastrado com sucesso!')
      registroStore.resetar()
      router.push(`/perfil-mulher/${uuidMulher}`)
    } catch (e) {
      toast.error('Erro ao cadastrar registro')
    }
  }
}
</script>
<style scoped>
</style>
