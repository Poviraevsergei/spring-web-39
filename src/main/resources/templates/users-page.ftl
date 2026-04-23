<#import "macros/userMacros.ftl" as um>
<#import "macros/bodyMacros.ftl" as bm>
<@bm.bodyMac>
    <h1>All users:</h1>
    <#list users as user>
        <@um.userMac user/>
        <p>----------------------------------------</p>
    </#list>
</@bm.bodyMac>