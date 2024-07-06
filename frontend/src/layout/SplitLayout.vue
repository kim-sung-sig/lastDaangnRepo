<template>
    <div class="container" ref="container">
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
            this.initialLeftPanelWidth = this.$refs.leftPanel.offsetWidth;

            document.addEventListener('mousemove', this.handleDragging);
            document.addEventListener('mouseup', this.endDragging);
        },
        handleDragging(event) {
            if (this.isDragging) {
                const containerWidth = this.$refs.container.offsetWidth;
                const offset = event.clientX - this.startX;
                const newLeftWidth = ((this.initialLeftPanelWidth + offset) / containerWidth) * 100;
                
                // 최소 너비와 최대 너비 설정
                if (newLeftWidth > 10 && newLeftWidth < 90) {
                    this.leftPanelWidth = `${newLeftWidth}%`;
                }
            }
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
