function HomeController(app) {
    app.controller('HomeController', function ($timeout) {
        $timeout(function () {
            new Splide('#banner', {
                type: 'loop',
                perPage: 1,
                perMove: 1,
                gap: 10,
                padding: '10%',
                arrows: true,
                pagination: true,
                focus: 'center',
                drag: 'free',
                snap: true,
            }).mount();
            new Splide('#blog', {
                type: 'loop',
                perPage: 2,
                breakpoints: {
                    1100: {
                        perPage: 1,
                        gap: 20,
                    },
                },
                padding: '10%',
                gap: 100,
                perMove: 1,
                arrows: true,
                pagination: true,
                focus: 'center',
                drag: 'free',
                snap: true,
            }).mount();
            new Splide('#brand', {
                type: 'loop',
                perPage: 7,
                perMove: 1,
                arrows: false,
                snap: true,
                breakpoints: {
                    1000: {
                        perPage: 4,
                    },
                    700: {
                        perPage: 3,
                    },
                },
                focus: 'center',
                drag: 'free',
                autoplay: true,
            }).mount();
            new Splide('#TopSell', {
                type: 'loop',
                perPage: 5,
                breakpoints: {
                    1100: {
                        perPage: 2,
                        gap: 20,
                    },
                },
                gap: 20,
                perMove: 1,
                arrows: true,
                pagination: false,
                focus: 'center',
                drag: 'free',
                snap: true,
            }).mount();
            (function () {
                const second = 1000,
                    minute = second * 60,
                    hour = minute * 60,
                    day = hour * 24;

                let today = new Date(),
                    dd = String(today.getDate()).padStart(2, '0'),
                    mm = String(today.getMonth() + 1).padStart(2, '0'),
                    yyyy = today.getFullYear(),
                    nextYear = yyyy + 1,
                    dayMonth = '10/30/',
                    birthday = dayMonth + yyyy;

                today = mm + '/' + dd + '/' + yyyy;
                if (today > birthday) {
                    birthday = dayMonth + nextYear;
                }

                const countDown = new Date(birthday).getTime(),
                    x = setInterval(function () {
                        const now = new Date().getTime(),
                            distance = countDown - now;
                        if (document.getElementById('days')) {
                            document.getElementById('days').innerText = '' + Math.floor(distance / day);
                        }
                        if (document.getElementById('hours')) {
                            document.getElementById('hours').innerText = '' + Math.floor((distance % day) / hour);
                        }
                        if (document.getElementById('minutes')) {
                            document.getElementById('minutes').innerText = '' + Math.floor((distance % hour) / minute);
                        }
                        if (document.getElementById('seconds')) {
                            document.getElementById('seconds').innerText = '' + Math.floor((distance % minute) / second);
                        }
                        if (distance < 0) {
                            if (document.getElementById('headline')) {
                                document.getElementById('headline').innerText = "It's my birthday!";
                            }
                            if (document.getElementById('countdown')) {
                                document.getElementById('countdown').style.display = 'none';
                            }
                            if (document.getElementById('content')) {
                                document.getElementById('content').style.display = 'block';
                            }
                            clearInterval(x);
                        }
                    }, 0);
            })();
        });
    });
}

export default HomeController;
