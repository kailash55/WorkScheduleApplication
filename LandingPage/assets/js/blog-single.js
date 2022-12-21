$(document).ready(function(){
    var blogId = getUrlVars()["id"]

    loadBlog(blogId)
})

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}

function renderBlog(blogData)
{
    $("#article-template").tmpl(blogData).appendTo("#article-container");
    $("#blog-content").html(blogData.body)
}

function loadBlog(blogId)
{
    $.ajax({
        url: "http://localhost:8085/"  + "blogs/"+blogId,
        type:"GET",
        contentType: "application/json; charset=utf-8",
        success: function(response)
        {
            console.log(response)
            renderBlog(response)
        },
        error: function(a,b,c)
        {
            console.log(a)
        }
    })
}