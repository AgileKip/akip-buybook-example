<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="buyBookApp.store.home.createOrEditLabel"
          data-cy="StoreCreateUpdateHeading"
          v-text="$t('buyBookApp.store.home.createOrEditLabel')"
        >
          Create or edit a Store
        </h2>
        <div>
          <div class="form-group" v-if="store.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="store.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.store.name')" for="store-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="store-name"
              data-cy="name"
              :class="{ valid: !$v.store.name.$invalid, invalid: $v.store.name.$invalid }"
              v-model="$v.store.name.$model"
              required
            />
            <div v-if="$v.store.name.$anyDirty && $v.store.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.store.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.store.name.minLength" v-text="$t('entity.validation.minlength', { min: 4 })">
                This field is required to be at least 4 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.store.name.maxLength" v-text="$t('entity.validation.maxlength', { max: 80 })">
                This field cannot be longer than 80 characters.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.store.address')" for="store-address">Address</label>
            <input
              type="text"
              class="form-control"
              name="address"
              id="store-address"
              data-cy="address"
              :class="{ valid: !$v.store.address.$invalid, invalid: $v.store.address.$invalid }"
              v-model="$v.store.address.$model"
              required
            />
            <div v-if="$v.store.address.$anyDirty && $v.store.address.$invalid">
              <small class="form-text text-danger" v-if="!$v.store.address.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.store.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./store-update.component.ts"></script>
