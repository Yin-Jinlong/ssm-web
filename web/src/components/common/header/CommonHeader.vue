<script setup lang="ts">


</script>

<template>
    <div class="header bg-dot-blur">
        <div class="header-wrapper">
            <div class="logo">
                <div class="logo-img"/>
                <div class="logo-text">
                    <div class="logo-text-zh">安阳师范学院</div>
                    <div class="logo-text-en">ANYANG NORMAL UNIVERSITY</div>
                </div>
            </div>

        </div>
    </div>
</template>

<style lang="scss" scoped>
@use 'sass:color';
@use '../../../yjl-var' as var;

$header-height : 100px;

.header {
  --header-height       : #{$header-height};
  --header-shadow-color : rgba(0, 0, 0, 0.2);
  --ds                  : 4px;

  position              : sticky;
  top                   : 0;
  left                  : 0;

  z-index               : 100;

  height                : var(--header-height);
  width                 : 100%;

  box-shadow            : var(--header-shadow-color) 0 0 3px;

}

html.dark {
  .header {
    --header-shadow-color : rgba(255, 255, 255, 0.2);
  }
}

.header-wrapper {
  display     : flex;
  align-items : center;
  min-width   : var(--min-width);
  max-width   : var(--max-width);
  height      : 100%;
  margin      : auto;

  > .logo {
    -webkit-user-drag : none;
    -webkit-user-select : none;
    -moz-user-select    : none;
    user-select: none;
    width             : max-content;
    height            : var(--header-height);
    display           : flex;
    align-items: center;

    > .logo-img {
      width            : var(--header-height);
      height           : var(--header-height);
      background-image : url('/img/logo.png');
      background-size  : var(--header-height) var(--header-height);
    }

    .logo-text {
      user-select         : none;
      color               : #{var.$color-aynu};
    }

    .logo-text-zh {
      font-family : 华文隶书, 隶书, serif;
      font-size   : 2.5em;
    }

    .logo-text-en {
      font-family : unset;
      font-size   : 0.98em;
    }

  }


}

</style>