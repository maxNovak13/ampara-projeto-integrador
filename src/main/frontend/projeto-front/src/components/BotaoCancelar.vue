<template>
  <button
      @click="router.push(destino)"
      class="bg-gray-300 text-gray-800 py-3 px-6 rounded hover:bg-gray-400 transition"
  >
    Cancelar
  </button>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
defineProps({
  destino: {
    type: String,
    default: '/inicio'
  }
})
</script>