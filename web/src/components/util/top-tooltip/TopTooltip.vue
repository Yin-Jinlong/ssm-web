<template>
    <el-tooltip
            placement="top"
            :content="props.content"
            :show-after="props.delay??100">
        <slot name="default">

        </slot>
    </el-tooltip>
</template>

<style scoped lang="scss">

</style>

<script setup lang="ts">

const props = defineProps<{
    content: string,
    delay?: number
}>()
</script>
