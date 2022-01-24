<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="buyBookBinding.processInstance">
        <h2 class="jh-entity-heading" data-cy="buyBookBindingDetailsHeading">
          <span v-text="$t('buyBookApp.buyBookBinding.detail.title')">BuyBookBinding</span> {{ buyBookBinding.id }}
        </h2>
        <akip-show-process-instance :processInstance="buyBookBinding.processInstance">
          <template v-slot:title>
            <span v-text="$t('buyBookApp.buyBookBinding.detail.title')">BuyBookBinding</span> #{{ buyBookBinding.id }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="buyBookBinding.buyBook">
              <h5 class="card-header">
                <span class="title"> Buy Book #{{ buyBookBinding.buyBook.id }} </span>
              </h5>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.purpose')">purpose</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="purpose"
                    id="buy-book-purpose"
                    data-cy="purpose"
                    v-model="buyBookBinding.buyBook.purpose"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.description')">description</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="description"
                    id="buy-book-description"
                    data-cy="description"
                    v-model="buyBookBinding.buyBook.description"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.date')">date</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="date"
                    id="buy-book-date"
                    data-cy="date"
                    v-model="buyBookBinding.buyBook.date"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.deliveryHome')">deliveryHome</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="deliveryHome"
                    id="buy-book-deliveryHome"
                    data-cy="deliveryHome"
                    v-model="buyBookBinding.buyBook.deliveryHome"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.deliveryAddress')">deliveryAddress</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="deliveryAddress"
                    id="buy-book-deliveryAddress"
                    data-cy="deliveryAddress"
                    v-model="buyBookBinding.buyBook.deliveryAddress"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.paymentDetails')">paymentDetails</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="paymentDetails"
                    id="buy-book-paymentDetails"
                    data-cy="paymentDetails"
                    v-model="buyBookBinding.buyBook.paymentDetails"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('buyBookApp.buyBookBinding.purchaseReview')">purchaseReview</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="purchaseReview"
                    id="buy-book-purchaseReview"
                    data-cy="purchaseReview"
                    v-model="buyBookBinding.buyBook.purchaseReview"
                  />
                </div>
              </div>
            </div>
          </template>
        </akip-show-process-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./buy-book-binding-details.component.ts"></script>
