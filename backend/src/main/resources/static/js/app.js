function loadPage(page, event) {
    if (event) {
        event.preventDefault(); // 이벤트 기본 동작 취소
        history.pushState(null, '', page); // URL 변경
    }

    console.log("page => " + page);

    axios.get(page)
    .then(res => {
        const data = res.data;
        const parser = new DOMParser();
        const doc = parser.parseFromString(data, 'text/html');

        // 콘텐츠 교체
        const content = doc.querySelector('#component').innerHTML;
        $('#content').html(content);

        // 타이틀 교체
        const newTitle = doc.querySelector('title').innerText;
        document.title = newTitle; // 페이지 타이틀 변경
        
        // navBtn 활성화
        const basePath = getBasePath(page);
        activeNabBtn(basePath); // navBtn 활성화
    })
    .catch(e => {
        console.error('Failed to load page', e);
        alert('Failed to load page');
    });

    $("#main_nav").show();
}

function getBasePath(path) {
    const parts = path.split('/');
    const basePath = '/' + parts[1];
    return basePath;
}

function activeNabBtn(page) {
    const navBtns = document.querySelectorAll('.navBtn');
    navBtns.forEach((btn) => {
        const btnHref = btn.getAttribute('href');
        if (btnHref === page) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });
}

// 페이지 로딩 시 현재 URL에 맞는 콘텐츠 로드
$(document).ready(function() {
    // popstate 이벤트 처리
    window.addEventListener('popstate', function(event) {
        console.log("뒤로가기 누름");
        const path = window.location.pathname;
        loadPage(path);
    });
});
