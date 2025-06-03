<template>
  <div class="max-w-3xl border border-gray-300 mx-auto mt-5 mb-5 p-6 bg-white rounded shadow-md">
    <h2 class="text-2xl font-bold text-purple-800 mb-4 text-center">Buscar Mulher</h2>

    <div class="flex flex-col md:flex-row md:items-center gap-4 mb-2">
      <select v-model="filtroSelecionado" class="border border-gray-400 p-2 rounded w-full md:w-1/3">
        <option value="nome">Nome</option>
        <option value="mae">Nome da Mãe</option>
        <option value="cpf">CPF</option>
        <option value="data">Data de Nascimento</option>
      </select>

      <!-- Busca por cpf-->
      <input
          v-if="filtroSelecionado === 'cpf'"
          v-model="valorBusca"
          type="text"
          class="border border-gray-400 p-2 rounded w-full md:flex-1"
          :placeholder="filtroSelecionado === 'cpf' ? '000.000.000-00' : 'Digite o valor da busca'"
          maxlength="14"
      />

      <!-- Busca por nome ou por name da mae-->
      <!--v-else-if="filtroSelecionado !== 'data'"-->
      <input
          v-else-if="filtroSelecionado === 'nome' || filtroSelecionado === 'mae'"
          v-model="valorBusca"
          type="text"
          class="border border-gray-400 p-2 rounded w-full md:flex-1"
          placeholder="Digite o valor da busca"
          maxlength="100"
      />

  <!-- Busca por data de nascimento-->
      <input
          v-else
          v-model="valorBusca"
          type="text"
          class="border border-gray-400 p-2 rounded w-full md:flex-1"
          :placeholder="filtroSelecionado === 'data' ? '00/00/0000' : 'Digite o valor da busca'"
      />

      <button
          @click="buscar"
          class="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700 transition"
      >
        Buscar
      </button>
    </div>

    <!-- Mensagem de erro -->
    <div v-if="erro" class="text-red-600 text-center mb-4">
      {{ erro }}
    </div>

    <!-- Lista dos resultados -->
    <div v-if="resultado.length" class="mt-6 space-y-4">
      <div
          v-for="mulher in resultado"
          :key="mulher.uuid"
          class="p-4 rounded shadow-md border border-gray-300 rounded flex items-center justify-between"
      >
        <div>
          <p><strong>Nome:</strong> {{ mulher.nome }}</p>
          <p><strong>Data de Nascimento:</strong> {{ formatarData(mulher.dataNascimento) }}</p>
          <p><strong>CPF:</strong> {{ mulher.cpf }}</p>
          <p><strong>Nome da mãe:</strong> {{ mulher.nomeMae }}</p>
        </div>
        <button
            @click.stop="irParaPerfil(mulher.uuid)"
            class="bg-purple-600 text-white px-4 py-4 rounded hover:bg-purple-700 transition"
        >
          Ver Perfil
        </button>
      </div>
    </div>

    <div v-else-if="buscou && !erro" class="text-center text-gray-500 mt-6">
      Nenhum resultado encontrado.
    </div>
  </div>
</template>

<script setup>
  import { ref, watch } from 'vue'
  import axios from 'axios'
  import { useRouter } from 'vue-router'

  const filtroSelecionado = ref('nome')
  const valorBusca = ref('')
  const resultado = ref([])
  const buscou = ref(false)
  const erro = ref('') // mensagem de erro
  const router = useRouter()

  function irParaPerfil(uuid) {
    router.push(`/perfil-mulher/${uuid}`)  // redireciona para a página de perfil
  }

  function formatarData(dataISO) {//formata data recebida do backend dos resultados para dd/mm/yyyy
    if (!dataISO) return "-"
    const [ano, mes, dia] = dataISO.split("-")
    return `${dia}/${mes}/${ano}`
  }

  //aplica mascara para data de nascimento
  function mascaraDataFormatada(valor) {
    valor = valor.replace(/\D/g, '').slice(0, 8)

    if (valor.length >= 5) {
      valor = valor.replace(/(\d{2})(\d{2})(\d{1,4})/, '$1/$2/$3')
    } else if (valor.length >= 3) {
      valor = valor.replace(/(\d{2})(\d{1,2})/, '$1/$2')
    }

    return valor
  }

  //wath para formatação de data digitada somente se o filtro for data
  watch([filtroSelecionado, valorBusca], ([filtro, valor]) => {
    if (filtro === 'data') {
      const valorFormatado = mascaraDataFormatada(valor)
      if (valorFormatado !== valor) {
        valorBusca.value = valorFormatado
      }
    }
  })

  // aplica máscara de CPF
  function mascaraCPF(valor) {
    valor = valor.replace(/\D/g, '') // tira o que não for número
    valor = valor.slice(0, 11)

    // Aplica a máscara: 000.000.000-00
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2')
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2')
    valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2')

    return valor
  }

  // watch para aplicar máscara somente se o filtro for CPF
  watch([filtroSelecionado, valorBusca], ([filtro, valor]) => {
    if (filtro === 'cpf') {
      const valorFormatado = mascaraCPF(valor)
      if (valorFormatado !== valor) {
        valorBusca.value = valorFormatado
      }
    }
  })

  async function buscar() {// faz a busca
    if (!valorBusca.value) {
      erro.value = 'Por favor, insira um valor para busca.'
      resultado.value = []
      buscou.value = false
      return
    }

    erro.value = ''  // limpa erro antes da busca
    buscou.value = false

    try {
      const { data } = await axios.get('http://localhost:8080/ampara/mulher/buscar', {
        params: {
          filtro: filtroSelecionado.value,//manda o filtro selecionado
          valor: valorBusca.value,
        },
      })

      resultado.value = data
      buscou.value = true
    } catch (err) { // mostra mensagem de erro vinda da API ou mensagem genérica
      erro.value = err.response?.data?.message || 'Erro ao buscar dados. Tente novamente.'
      resultado.value = []
      buscou.value = true
    }
  }

</script>