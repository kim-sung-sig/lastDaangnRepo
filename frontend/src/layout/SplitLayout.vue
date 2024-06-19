<template>
    <div class="container">
        <div id="left" :style="{ width: leftFlexValue }">
            <slot name="left"></slot>
        </div>
        <div class="bar" @mousedown="startDragging($event)"></div>
        <div id="right" :style="{ width: rightFlexValue }">
            <slot name="right"></slot>
        </div>
    </div>
</template>
// 수정해야댐!
<script>
export default {
    data() {
        return {
            startX: 0,
            isDragging: false,
            leftFlexValue: "50%",
            rightFlexValue: "50%",
        };
    },
    computed: {
        // eslint-disable-next-line vue/no-dupe-keys
        leftFlex() {
            return this.leftFlex;
        },
        // eslint-disable-next-line vue/no-dupe-keys
        rightFlex() {
            return this.rightFlex;
        },
    },
    methods: {
        startDragging(event) {
            this.dragging = true;
            this.startX = event.clientX;
            document.addEventListener("mousemove", this.handleDragging);
            document.addEventListener("mouseup", this.stopDragging);
        },
        handleDragging(event) {
            if (this.dragging) {
                const diffX = event.clientX - this.startX;
                if (diffX !== 0) {
                    // 계산된 flex 값
                    const newLeftFlex = this.startLeftFlex + diffX / 10;
                    const newRightFlex = this.startRightFlex - diffX / 10;

                    // 최소 flex 값 제한
                    if (newLeftFlex >= 1 && newRightFlex >= 1) {
                        this.startLeftFlex = newLeftFlex;
                        this.startRightFlex = newRightFlex;
                    }
                }
                this.startX = event.clientX;
            }
        },
        stopDragging() {
            this.dragging = false;
            document.removeEventListener("mousemove", this.handleDragging);
            document.removeEventListener("mouseup", this.stopDragging);
        },
    },
};
</script>

<style scoped>
.container {
    display: flex;
    height: 100%;
    width: 100%;
}

.left,
.right {
    overflow: auto;
    padding: 20px;
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
