// function post() {
//     const http =(function () {
//         const send = (url, method, body) => fetch(url, {method, body: JSON.stringify(body)})
//             .then(response => response.json());
//         const post = (url, body) =>send(url, 'POST', body);
//         const get = (url) => send (url, 'GET', null);
//         return{
//             send, post, get
//         };
//
//     }());
//     $('#form').on('submit', (ev) => {
//         const id = $('#btn-id').val();
//         // const id = $(this).attr('data-id');
//         http.post(url, id)
//             .then(() => f(id));
//         ev.preventDefault();
//         return false;
//     })
// }



function slideshow() {
    $('.carousel').carousel({
            dist: 0,
            padding: 0,
            fullWidth: true,
            indicators: true,
            duration: 100
        }
    );
    autoplay();

    function autoplay() {
        $('.carousel').carousel('next');
        setTimeout(autoplay, 4500);
    }
}

function post() {
    const http =(function () {
        const send = (url, method, body) => fetch(url, {method, body: JSON.stringify(body)})
            .then(response => response.json());
        const post = (url, body) =>send(url, 'POST', body);
        const get = (url) => send (url, 'GET', null);
        return{
            send, post, get
        };

    }());
    $('#form').on('submit', (ev) => {
        const id = $('#btn-id').val();
        // const id = $(this).attr('data-id');
        http.post(url, id)
            .then(() => window.location.replace('/home'));
        ev.preventDefault();
        return false;
    })
}

// function f(id) {
//     http.get(url, id).send(url, {method: 'GET', id})
//
// }


function showGallery(id) {
    fetch("/events/all-pictures/" + id)
        .then((response) => response.json()
            .then((json) => {
                $('#content-container').remove();
                $('.custom-container').append(`<div id="content-container"></div>`);

                console.log(json);
                json.forEach((image, index) => {
                    $('#content-container')
                        .append(`<img src="${image.url}" width="200px" height="200px">`);
                    console.log(image.url);
                });
            }))
        .catch(() => {
            $('.container #content-container').append(`<h2>There are no pictures in the event gallery!</h2>`)
        })

}

function showAttendingEvents() {
    fetch("/events/my-events/attending")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Attending events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h2>Event: ${event.name}</h2>
 <h3>Starts: ${event.startsOnDate.toString()} at ${event.startsOnTime.toString()} </h2>
 <hr class="border"/>
 <a href="/events/my-events/attending/${event.id}" class="btn-flat btn-info rounded">View</a>`);
            });
        })
        .catch(() => {
            $('#customText').text('Attending events');
        });

}


function showCreatedEvents() {
    fetch("/events/my-events/created")
        .then((response) => response.json())
        .then((json) => {
            $('#customText').text('Created events');
            $('#content-container').remove();
            $('.custom-container').append(`<div id="content-container" class="mt-4 text-center"></div>`);

            json.forEach((event, index) => {
                $('#content-container').append(
                    `<h2> Event: ${event.name}</h2>
<h3>Starts: ${event.startsOnDate.toString()} at ${event.startsOnTime.toString()} </h3>
<hr class="border"/>
<a href="/events/my-events/created/${event.id}" class="btn-flat btn-info ml-3">View</a>
<a href="/events/my-events/created/edit/${event.id}" class="btn-flat btn-success rounded ml-3">Edit</a>
<a href="/events/my-events/created/delete/${event.id}" class="btn-flat btn-danger rounded ml-3">Delete</a><hr/>`);
            });
        })
        .catch(() => {
            $('#customText').text('Created events');
        });


}

function myFunction() {
    $('input').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });

    $('select').focus(function () {
        $(this).prev().addClass('stylee');
    }).blur(function () {
        if ($(this).val()) {
            $(this).prev().addClass('stylee');
        } else {
            $(this).prev().removeClass('stylee');
        }
    });
}