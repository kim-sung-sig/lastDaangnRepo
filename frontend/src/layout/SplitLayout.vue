<template>
    <div id="splitter">
        <div class="split split-horizontal" ref="pane1">
            <slot name="left"></slot>
        </div>
        <div class="split split-horizontal" ref="pane2">
            <slot name="right"></slot>
        </div>
    </div>
</template>

<script>
import Split from "split.js";

export default {
    name: "App",
    mounted() {

        Split([this.$refs.pane1, this.$refs.pane2], {
        sizes: [50, 50],
        minSize: [400, Infinity], // 왼쪽 최소 사이즈는 200px, 오른쪽은 100px
        maxSize: [window.innerWidth * 0.5, Infinity], // 왼쪽 최대 사이즈는 50%, 오른쪽은 제한 없음
        gutterSize: 10,
        direction: "horizontal",
        });
    },
};
</script>

<style>
#splitter {
    display: flex;
    width: 100%;
    height: calc(100vh - 40px);
}

.split {
    overflow: auto;
}

.split-horizontal {
    flex-grow: 1;
}
.gutter{
    cursor: col-resize;
    background: #333;
}
</style>
