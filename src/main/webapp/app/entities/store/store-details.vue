<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="store">
        <h2 class="jh-entity-heading" data-cy="storeDetailsHeading">
          <span v-text="$t('buyBookApp.store.detail.title')">Store</span> {{ store.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('buyBookApp.store.name')">Name</span>
          </dt>
          <dd>
            <span>{{ store.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.store.address')">Address</span>
          </dt>
          <dd>
            <span>{{ store.address }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="store.id" :to="{ name: 'StoreEdit', params: { storeId: store.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./store-details.component.ts"></script>
