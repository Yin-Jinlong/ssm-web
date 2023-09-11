<script setup lang="ts">
import {Plus} from "@element-plus/icons-vue";
import {TopTooltip} from "@components";

</script>

<template>
    <div class="add-btn-frame">
        <div class="add-btn-container">
            <div class="add-btn-container-wrapper">
                <top-tooltip
                        content="添加">
                    <el-button
                            class="add-btn"
                            type="primary">
                        <el-icon class="max-size">
                            <Plus/>
                        </el-icon>
                    </el-button>
                </top-tooltip>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss">

.add-btn-frame {
  position : absolute;
  top      : 0;
  width    : 100%;
  height   : 100vh;
}

.add-btn-container {
  min-width : var(--min-width);
  max-width : var(--max-width);
  height    : 100vh;
  position  : relative;
  margin    : auto;
}

.add-btn-container-wrapper {
  position : absolute;
  bottom   : 1em;
  right    : 2em;
  z-index  : 90;
}

.add-btn {
  display         : flex;
  align-items     : center;
  justify-content : center;
  width           : 40px;
  height          : 40px;
  border-radius   : 100%;
}

</style>