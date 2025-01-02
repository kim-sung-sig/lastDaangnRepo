// router.js
import Home from '../pages/home/Home.js';
import MyPage from '../pages/mypage/MyPageView.js';
import TTest from '../pages/mypage/Test.js';

const routes = [
    { path: '/', name: "home", component: Home },
    { path: '/my', name: "my", component: MyPage },
    { path: '/my/test', name: "test", component: TTest },
];

export default routes;