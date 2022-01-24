<template>
  <div>
    <h2 id="page-heading" data-cy="StoreHeading">
      <span v-text="$t('buyBookApp.store.home.title')" id="store-heading">Stores</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('buyBookApp.store.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'StoreCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-store"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('buyBookApp.store.home.createLabel')"> Create a new Store </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && stores && stores.length === 0">
      <span v-text="$t('buyBookApp.store.home.notFound')">No stores found</span>
    </div>
    <div class="table-responsive" v-if="stores && stores.length > 0">
      <table class="table table-striped" aria-describedby="stores">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('buyBookApp.store.name')">Name</span></th>
            <th scope="row"><span v-text="$t('buyBookApp.store.address')">Address</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="store in stores" :key="store.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'StoreView', params: { storeId: store.id } }">{{ store.id }}</router-link>
            </td>
            <td>{{ store.name }}</td>
            <td>{{ store.address }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'StoreView', params: { storeId: store.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'StoreEdit', params: { storeId: store.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(store)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="buyBookApp.store.delete.question" data-cy="storeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-store-heading" v-text="$t('buyBookApp.store.delete.question', { id: removeId })">
          Are you sure you want to delete this Store?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-store"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeStore()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./store.component.ts"></script>
