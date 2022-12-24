$(document).ready(function(){
    loadBlogs()
})

function loadBlogs(){
    $.ajax({
        url: "http://stafferin.com/api/"  + "blogs",
        type:"GET",
        contentType: "application/json; charset=utf-8",
        success: function(response)
        {
            console.log(response)
            renderBlogs(response)
        },
        error: function(a,b,c)
        {
            console.log(a)
        }
    })
}

function renderBlogs(blogs)
{
    for(let i=0; i<=blogs.length; i++)
    {
        var articleData = blogs[i]
        $("#article-template").tmpl(articleData).appendTo("#article-container");
    }
}