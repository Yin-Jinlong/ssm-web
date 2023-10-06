<template>
    <router-view></router-view>
</template>

<style scoped>

</style>
<script setup lang="ts">
import {RouterView} from 'vue-router'
import {useGlobalStatuser} from "@util/Statuser.ts";
import {watch} from "vue";

const gs = useGlobalStatuser()

const dark = gs.useRef('dark', false)

const docEle = document.documentElement

function change() {
    if (dark.value)
        docEle.classList.add('dark')
    else
        docEle.classList.remove('dark')
}

watch(dark, change)

change()

</script>
