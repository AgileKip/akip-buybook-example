<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="buyBookApp.buyBook.home.createOrEditLabel"
          data-cy="BuyBookCreateUpdateHeading"
          v-text="$t('buyBookApp.buyBook.home.createOrEditLabel')"
        >
          Create or edit a BuyBook
        </h2>
        <div>
          <div class="form-group" v-if="buyBook.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="buyBook.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.purpose')" for="buy-book-purpose">Purpose</label>
            <input
              type="text"
              class="form-control"
              name="purpose"
              id="buy-book-purpose"
              data-cy="purpose"
              :class="{ valid: !$v.buyBook.purpose.$invalid, invalid: $v.buyBook.purpose.$invalid }"
              v-model="$v.buyBook.purpose.$model"
              required
            />
            <div v-if="$v.buyBook.purpose.$anyDirty && $v.buyBook.purpose.$invalid">
              <small class="form-text text-danger" v-if="!$v.buyBook.purpose.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.description')" for="buy-book-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="buy-book-description"
              data-cy="description"
              :class="{ valid: !$v.buyBook.description.$invalid, invalid: $v.buyBook.description.$invalid }"
              v-model="$v.buyBook.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.date')" for="buy-book-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="buy-book-date"
                  v-model="$v.buyBook.date.$model"
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
                id="buy-book-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.buyBook.date.$invalid, invalid: $v.buyBook.date.$invalid }"
                v-model="$v.buyBook.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.deliveryHome')" for="buy-book-deliveryHome"
              >Delivery Home</label
            >
            <input
              type="checkbox"
              class="form-check"
              name="deliveryHome"
              id="buy-book-deliveryHome"
              data-cy="deliveryHome"
              :class="{ valid: !$v.buyBook.deliveryHome.$invalid, invalid: $v.buyBook.deliveryHome.$invalid }"
              v-model="$v.buyBook.deliveryHome.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.deliveryAddress')" for="buy-book-deliveryAddress"
              >Delivery Address</label
            >
            <input
              type="text"
              class="form-control"
              name="deliveryAddress"
              id="buy-book-deliveryAddress"
              data-cy="deliveryAddress"
              :class="{ valid: !$v.buyBook.deliveryAddress.$invalid, invalid: $v.buyBook.deliveryAddress.$invalid }"
              v-model="$v.buyBook.deliveryAddress.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.paymentDetails')" for="buy-book-paymentDetails"
              >Payment Details</label
            >
            <input
              type="text"
              class="form-control"
              name="paymentDetails"
              id="buy-book-paymentDetails"
              data-cy="paymentDetails"
              :class="{ valid: !$v.buyBook.paymentDetails.$invalid, invalid: $v.buyBook.paymentDetails.$invalid }"
              v-model="$v.buyBook.paymentDetails.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.purchaseReview')" for="buy-book-purchaseReview"
              >Purchase Review</label
            >
            <input
              type="text"
              class="form-control"
              name="purchaseReview"
              id="buy-book-purchaseReview"
              data-cy="purchaseReview"
              :class="{ valid: !$v.buyBook.purchaseReview.$invalid, invalid: $v.buyBook.purchaseReview.$invalid }"
              v-model="$v.buyBook.purchaseReview.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.book')" for="buy-book-book">Book</label>
            <select class="form-control" id="buy-book-book" data-cy="book" name="book" v-model="buyBook.book">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="buyBook.book && bookOption.id === buyBook.book.id ? buyBook.book : bookOption"
                v-for="bookOption in books"
                :key="bookOption.id"
              >
                {{ bookOption.name }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.buyBook.store')" for="buy-book-store">Store</label>
            <select class="form-control" id="buy-book-store" data-cy="store" name="store" v-model="buyBook.store">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="buyBook.store && storeOption.id === buyBook.store.id ? buyBook.store : storeOption"
                v-for="storeOption in stores"
                :key="storeOption.id"
              >
                {{ storeOption.name }}
              </option>
            </select>
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
            :disabled="$v.buyBook.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./buy-book-update.component.ts"></script>
