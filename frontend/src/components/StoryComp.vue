
<template>
    <main>
        <div>
            <p>{{ username }}({{ id }})</p>
            <p class="text-center w-12 text-xs truncate">{{ username }}</p>
        </div>
    </main>
</template>

<script>
export default {
    name: 'Story-main',
    props: {
        id: {
            type : String,
            required: true
        },
        username: {
            type : String,
            required: true
        }
    }
}
</script>