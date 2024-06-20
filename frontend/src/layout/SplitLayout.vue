<template>
    <div class="container">
        <div class="left" :style="{ width: leftPanelWidth }" ref="leftPanel">
            <slot name="left"></slot>
        </div>
        <div class="bar" @mousedown="startDragging"></div>
        <div class="right" :style="{ width: computedRightPanelWidth }" ref="rightPanel">
            <slot name="right"></slot>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            isDragging: false,
            startX: 0,
            initialLeftPanelWidth: '50%', // 초기 좌측 패널 너비
            leftPanelWidth: '50%', // 좌측 패널 너비
            rightPanelWidth: '50%' // 우측 패널 너비
        };
    },
    computed: {
        computedRightPanelWidth() {
            return `calc(100% - ${this.leftPanelWidth} - 4px)`;
        }
    },
    methods: {
        startDragging(event) {
            this.isDragging = true;

            this.startX = event.clientX;
            console.log(this.startX);

            document.addEventListener('mousemove', this.handleDragging);
            document.addEventListener('mouseup', this.endDragging);
        },
        handleDragging(event) {
            console.log(event);
            this.leftPanelWidth = event.clientX;
            /*
            if (this.isDragging) {
                const offset = event.clientX - this.startX;
                const newLeftWidth = (parseFloat(this.leftPanelWidth) + offset / containerWidth * 100).toFixed(2) + '%';

                this.leftPanelWidth = newLeftWidth;
            }
                */
        },
        endDragging() {
            this.isDragging = false;
            document.removeEventListener('mousemove', this.handleDragging);
            document.removeEventListener('mouseup', this.endDragging);
        }
    }
};
</script>

<style scoped>
.container {
    display: flex;
    height: 100%;
    width: 100%;
}

.left, .right {
    overflow: auto;
    padding: 20px;
    min-width: 50px; /* 최소 너비 설정 */
}

.left {
    background-color: #f0f0f0;
}

.right {
    background-color: #e0e0e0;
}

.bar {
    cursor: col-resize;
    width: 4px;
    background-color: #333;
}
</style>
