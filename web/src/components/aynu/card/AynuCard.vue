<template>
    <el-card style="padding: 3em 1em 1em 1em;position: relative;width: calc(100% - 2em)">
        <div class="card-content">
            <div class="base-info">
                <el-image
                        class="avatar"
                        :src="props.data.img"/>
                <div class="name">{{ props.data.name }}</div>
            </div>
            <div class="msg">
                {{ props.data.msg }}
            </div>
            <div class="time">
                {{props.data.time.toLocaleDateString()}}
                {{props.data.time.toLocaleTimeString()}}
            </div>
        </div>
        <div class="card-btn-container">
            <div class="card-btn">
                <top-tooltip
                        content="编辑">
                    <el-button><span>编辑</span></el-button>
                </top-tooltip>
            </div>
            <top-tooltip
                    content="删除">
                <div class="card-btn">
                    <el-popconfirm
                            :width="100"
                            :confirm-button-type="'danger'"
                            @confirm="onDelete"
                            title="确认删除?">
                        <template #reference>

                            <el-button
                                    type="danger"><span>删除</span></el-button>
                        </template>
                    </el-popconfirm>
                </div>
            </top-tooltip>
        </div>
    </el-card>
</template>

<style scoped lang="scss">
@use '../../../yjl-fun' as f;
@use 'element-plus/theme-chalk/src/card';

.card-content {
  display : flex;
  color   : f.blackAlpha(0.8);
}

.base-info {
  width        : max-content;
  border-right : #{f.blackAlpha(0.2)} solid 1px;
}

.time{
  position: absolute;
  top: 0.9em;
  left: 140px;
  color: rgba(128,128,128,0.5);
}

html.dark {
  .base-info {
    border-right : #{f.whiteAlpha(0.2)} solid 1px;
  }

  .card-content {
    color : f.whiteAlpha(0.8);
  }
}

.avatar {
  width         : 60px;
  border-radius : 100%;
  margin        : 0 2em 0 1em;
}

.name {
  align-items        : center;
  margin-top         : 1em;
  width              : calc(60px + 2em);
  height             : 2em;
  text-align         : center;
  line-height        : 1em;
  // 超出部分省略号
  word-break         : break-all;
  overflow           : hidden;
  display            : -webkit-box;
  -webkit-line-clamp : 2;
  -webkit-box-orient : vertical;
}

.card-btn-container {
  position : absolute;
  display  : flex;
  top      : 5px;
  right    : 0.5em;
}

.card-btn {
  margin-left : 1em;
}

.msg {
  margin-left   : 2em;
  width         : 100%;
  padding       : 3px;
  border        : rgba(211, 231, 179, 0.45) solid 1px;
  border-radius : 10px;
}

</style>

<script setup lang="ts">

import {TopTooltip} from "@components";

const props = defineProps<{
    data: {
        name: string
        img: string
        msg: string
        time:Date
    },
    onDelete: () => void
}>()
</script>
