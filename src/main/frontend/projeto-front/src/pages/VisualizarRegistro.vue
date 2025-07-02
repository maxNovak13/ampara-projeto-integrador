<<template>
  <div class="max-w-5xl mx-auto p-6">
    <h1 class="text-2xl font-bold text-purple-800 mb-6">Detalhes do Registro</h1>

    <div v-if="registro" class="bg-white border border-gray-300 rounded-xl shadow-lg p-6 space-y-6">

      <!-- Título com nome da mulher e código -->
      <div>
        <h2 class="text-xl font-bold text-purple-800 text-center mb-6">
          {{ registro.nomeMulher }} - {{ formatarCodigo(registro.creatAt, registro.codigo) }}
        </h2>
      </div>

      <!-- Informações gerais do registro -->
      <div class="grid grid-cols-1 sm:grid-cols-3 gap-3 mb-6 pl-5 pr-5">
        <div class="text-base">
          <p class="text-black font-medium">Data do Ocorrido</p>
          <p class="text-gray-800">{{ registro.dataOcorrido }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Violência</p>
          <p class="text-gray-800">{{ registro.violencia ? 'Sim' : 'Não' }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Tipo de Violência</p>
          <p class="text-gray-800">{{ registro.tipoViolencia }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Relação com a Vítima</p>
          <p class="text-gray-800">{{ registro.relacaoVitima }}</p>
        </div>
        <div class="text-base">
          <p class="text-black font-medium">Lugar do Ocorrido</p>
          <p class="text-gray-800">{{ registro.lugar }}</p>
        </div>
      </div>

      <!-- Depoimento -->
      <div class="text-base pl-5 pr-5">
        <p class="text-black font-medium">Depoimento</p>
        <p class="text-gray-800 mt-1">{{ registro.depoimento }}</p>
      </div>

      <!-- Seção de Atendimento -->
      <div class="border border-gray-300 rounded-xl p-6 space-y-6">
        <h2 class="text-2xl font-bold text-purple-800 text-center">Atendimento</h2>

        <!-- Linha com data, local e profissional -->
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          <div class="text-base">
            <p class="text-black font-medium">Data do Atendimento</p>
            <p class="text-gray-800">{{ registro.creatAt }}</p>
          </div>
          <div class="text-base">
            <p class="text-black font-medium">Local de Atendimento</p>
            <p class="text-gray-800">{{ registro.localAtendimento }}</p>
          </div>
          <div class="text-base">
            <p class="text-black font-medium">Profissional Responsável</p>
            <p class="text-gray-800">{{ registro.nomeProfissional }}</p>
          </div>
        </div>

        <div class="text-base">
          <p class="text-black font-medium">Descrição do profissional</p>
          <p class="text-gray-800 mt-1">{{ registro.descricao }}</p>
        </div>

        <div class="text-base">
          <p class="text-black font-medium">Conduta</p>
          <p class="text-gray-800 mt-1">{{ registro.conduta }}</p>
        </div>

        <div class="text-base">
          <p class="text-black font-medium">Encaminhamento</p>
          <p class="text-gray-800 mt-1">{{ registro.encaminhamento }}</p>
        </div>
      </div>

      <!-- Agressores -->
      <ListaAgressores
          :agressores="registro.agressores"
          :exibirBotaoExcluir="false"
      />
    </div>

    <div v-else class="text-gray-600">Carregando registro...</div>

    <BotaoVoltar></BotaoVoltar>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import axios from 'axios'
import BotaoVoltar from '../components/BotaoVoltar.vue'
import ListaAgressores from '../components/ListaAgressores.vue'

const route = useRoute()
const registro = ref(null)

const formatarCodigo = (data, codigo) => {
  const partes = data?.split('/')
  const ano = partes?.[2] || '----'
  return `REG-${ano}-${codigo.toString().padStart(4, '0')}`
}

const formatarEndereco = (endereco) => {
  if (!endereco) return ''
  return `${endereco.rua}, nº ${endereco.numero}, ${endereco.bairro} - ${endereco.complemento}`
}

onMounted(async () => {
  const uuid = route.params.uuid
  try {
    const token = localStorage.getItem('token')
    const res = await axios.get(`http://localhost:8080/ampara/registro/buscar-reg/${uuid}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
    registro.value = res.data
  } catch (err) {
    console.error('Erro ao buscar registro:', err)
  }
})

</script>

<style scoped>

</style>