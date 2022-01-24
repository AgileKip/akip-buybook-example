<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="buyBookApp.startForm.home.createOrEditLabel"
          data-cy="StartFormCreateUpdateHeading"
          v-text="$t('buyBookApp.startForm.home.createOrEditLabel')"
        >
          Create or edit a StartForm
        </h2>
        <div v-if="buyBookBinding.processInstance">
          <akip-show-process-definition :processDefinition="buyBookBinding.processInstance.processDefinition">
            <template v-slot:body>
              <hr />
              <div v-if="buyBookBinding.buyBook">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.startForm.purpose')" for="start-form-purpose">Purpose</label>
                  <input
                    type="text"
                    class="form-control"
                    name="purpose"
                    id="start-form-purpose"
                    data-cy="purpose"
                    :class="{ valid: !$v.buyBookBinding.buyBook.purpose.$invalid, invalid: $v.buyBookBinding.buyBook.purpose.$invalid }"
                    v-model="$v.buyBookBinding.buyBook.purpose.$model"
                    required
                  />
                  <div v-if="$v.buyBookBinding.buyBook.purpose.$anyDirty && $v.buyBookBinding.buyBook.purpose.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!$v.buyBookBinding.buyBook.purpose.required"
                      v-text="$t('entity.validation.required')"
                    >
                      This field is required.
                    </small>
                  </div>
                </div>
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.startForm.description')" for="start-form-description"
                    >Description</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    name="description"
                    id="start-form-description"
                    data-cy="description"
                    :class="{
                      valid: !$v.buyBookBinding.buyBook.description.$invalid,
                      invalid: $v.buyBookBinding.buyBook.description.$invalid,
                    }"
                    v-model="$v.buyBookBinding.buyBook.description.$model"
                  />
                </div>
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.startForm.date')" for="start-form-date">Date</label>
                  <b-input-group class="mb-3">
                    <b-input-group-prepend>
                      <b-form-datepicker
                        aria-controls="start-form-date"
                        v-model="$v.buyBookBinding.buyBook.date.$model"
                        name="date"
                        class="form-control"
                        :locale="currentLanguage"
                        button-only
                        today-button
                        reset-button
                        close-button
                      >
                      </b-form-datepicker>
                    </b-input-group-prepend>
                    <b-form-input
                      id="start-form-date"
                      data-cy="date"
                      type="text"
                      class="form-control"
                      name="date"
                      :class="{ valid: !$v.buyBookBinding.buyBook.date.$invalid, invalid: $v.buyBookBinding.buyBook.date.$invalid }"
                      v-model="$v.buyBookBinding.buyBook.date.$model"
                    />
                  </b-input-group>
                </div>
              </div>
            </template>
          </akip-show-process-definition>
          <br />
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.buyBookBinding.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./start-form-init.component.ts"></script>
