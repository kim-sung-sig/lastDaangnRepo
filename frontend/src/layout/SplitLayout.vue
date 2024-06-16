<template>
    <div class="split-layout">
        <div id="left-pane">
            <slot name="left"></slot>
        </div>

        <div id="right-pane">
            <slot name="right"></slot>
        </div>
    </div>
</template>

<script>
import Split from 'split.js';

export default {
    mounted() {
        this.initializeSplit();
    },
    methods: {
        initializeSplit() {
            Split(['#left-pane', '#right-pane'], {
                sizes: ['30%', '70%'],
                minSize: [200, 700],
                gutterSize: 4,
                cursor: 'col-resize',
                direction: 'horizontal',
                gutter: function (index, direction) {
                    const gutter = document.createElement('div');
                    gutter.className = `gutter gutter-${direction}`;
                    return gutter;
                }
            });
        }
    }
};

</script>

<style scoped>
.split-layout {
    display: flex;
    height: 100%;
    width: 100%;
}

#left-pane, #right-pane {
    overflow: auto;
    padding: 20px;
}

#left-pane {
    background-color: #f0f0f0;
}

#right-pane {
    background-color: #e0e0e0;
}
.gutter {
    background-color: #ccc;
    background-repeat: no-repeat;
    background-position: 50%;
}

.gutter.gutter-horizontal {
    cursor: col-resize;
    width: 4px;
}
</style>