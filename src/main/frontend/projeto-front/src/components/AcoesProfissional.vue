<template>
  <div class="flex flex-col gap-2">

    <!-- SITUAÇÂO = Pendente: Aprovar ou Rejeitar acesso -->
    <template v-if="prof.situacao === 'PENDENTE'">
      <button
          @click="atualizarSituacao('ATIVO')"
          class="bg-green-200 text-green-800 px-3 py-1 rounded hover:bg-green-300 font-medium"
      >
        Aprovar
      </button>
      <button
          @click="atualizarSituacao('INATIVO')"
          class="bg-gray-200 text-gray-600 px-3 py-1 rounded hover:bg-gray-300 font-medium"
      >
        Rejeitar
      </button>
    </template>

    <!-- SITUAÇÂO = Ativo: Tornar adm / Tirar acesso de adm -->
    <template v-else-if="prof.situacao === 'ATIVO'">
      <button
          v-if="!prof.adm"
          @click="atualizarAdm(true)"
          class="bg-blue-200 text-blue-800 px-3 py-1 rounded hover:bg-blue-300 font-medium"
      >
        Tornar administrador
      </button>
      <button
          v-else
          @click="atualizarAdm(false)"
          class="bg-yellow-100 text-yellow-800 px-3 py-1 rounded hover:bg-yellow-200 font-medium"
      >
        Tirar acesso de adm
      </button>

      <button
          @click="atualizarSituacao('INATIVO')"
          class="bg-red-200 text-red-800 px-3 py-1 rounded hover:bg-red-300 font-medium"
      >
        Tirar acesso
      </button>
    </template>

    <!-- SITUAÇÂO = Inativo: Dar acesso -->
    <template v-else-if="prof.situacao === 'INATIVO'">
      <button
          @click="atualizarSituacao('ATIVO')"
          class="bg-orange-200 text-orange-800 px-3 py-1 rounded hover:bg-orange-300 font-medium"
      >
        Dar acesso
      </button>
    </template>
  </div>
</template>

<script setup>
import axios from 'axios'
const props = defineProps({
  prof: Object,
  recarregar: Function
})

async function atualizarSituacao(novaSituacao) {
  try {
    console.log("Nova situacao ", novaSituacao, " uuid ", props.prof.uuid)
    await axios.patch(`http://localhost:8080/ampara/profissional/${props.prof.uuid}/situacao`, null, {
      params: { nova: novaSituacao }
    })
    await props.recarregar()
  } catch (error) {
    console.error('Erro ao atualizar situação:', error)
  }
}

async function atualizarAdm(novoValor) {
  try {
    await axios.patch(`http://localhost:8080/ampara/profissional/${props.prof.uuid}/boolean`, null, {
      params: { adm: novoValor }
    })
    await props.recarregar()
  } catch (error) {
    console.error('Erro ao atualizar administrador:', error)
  }
}
</script>

<style>

</style>
