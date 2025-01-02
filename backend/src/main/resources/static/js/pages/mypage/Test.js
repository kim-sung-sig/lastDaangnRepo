export default {
    template: `
        <transition name="slide-up">
            <div v-if="isVisible" class="detail-container" style="background-color: #ccc; height: 100vh;">
                <div
                    v-if="isLoading"
                    class="spinner"
                    style="display: flex; justify-content: center; align-items: center; height: 100%; font-size: 1.5rem; color: #999;">
                    <!-- 스피너 아이콘 -->
                    <span>Loading...</span>
                </div>
                <div v-else class="detail-content">
                    <h1>My Page(Test)</h1>
                    <p>This is My Page(Test)</p>
                    <!-- 디테일 화면 -->
                    <h1>{{ detailTitle }}</h1>
                    <p>{{ detailContent }}</p>
                </div>
            </div>
        </transition>
    `,
    data() {
        return {
            isVisible: false, // 화면 표시 여부
            isLoading: true,  // 로딩 상태
            detailTitle: '',
            detailContent: '',
        }
    },
    methods: {
        loadDetailData() {
            // 가상 데이터 로드 시뮬레이션
            setTimeout(() => {
                this.detailTitle = '디테일 제목';
                this.detailContent = '이것은 디테일 내용입니다.';
                this.isLoading = false; // 로딩 완료 후 스피너 숨김
            }, 2000); // 2초 동안 로딩 시뮬레이션
        },
    },
    mounted() {
        this.isVisible = true, // 화면 표시 여부
        this.loadDetailData();
    },
}