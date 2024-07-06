function loadPage(page, event) {
    if (event) {
        event.preventDefault(); // 이벤트 기본 동작 취소
        history.pushState(null, '', '/' + page); // URL 변경
    }

    axios.get('/' + page)
    .then(res => {
        const data = res.data;
        const parser = new DOMParser();
        const doc = parser.parseFromString(data, 'text/html');
        const content = doc.querySelector('#component').innerHTML;
        $('#content').html(content);
        activeNabBtn(page);
    })
    .catch(e => {
        console.error('Failed to load page', e);
        alert('Failed to load page');
    });
}
function activeNabBtn(page) {
    const navBtns = document.querySelectorAll('.navBtn');
    navBtns.forEach((btn) => {
        const btnHref = btn.getAttribute('href');
        if (btnHref === '/' + page) {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });
}
// popstate 이벤트 처리
window.addEventListener('popstate', function(event) {
    console.log("뒤로가기 누름")
    const path = window.location.pathname.substring(1);
    loadPage(path);
});

// 페이지 로딩 시 현재 URL에 맞는 콘텐츠 로드
$(document).ready(function() {
    const path = window.location.pathname.substring(1);
    activeNabBtn(path);

    if (path) {
        axios.get('/' + path)
        .then(function (response) {
            $('#content').html($(response.data).find('#compnent').html());
        })
        .catch(function (error) {
            console.error('Failed to load page', error);
            alert('Failed to load page');
        });
    }
});
