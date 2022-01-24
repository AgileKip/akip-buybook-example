<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="buyBook">
        <h2 class="jh-entity-heading" data-cy="buyBookDetailsHeading">
          <span v-text="$t('buyBookApp.buyBook.detail.title')">BuyBook</span> {{ buyBook.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('buyBookApp.buyBook.purpose')">Purpose</span>
          </dt>
          <dd>
            <span>{{ buyBook.purpose }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.description')">Description</span>
          </dt>
          <dd>
            <span>{{ buyBook.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.date')">Date</span>
          </dt>
          <dd>
            <span>{{ buyBook.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.deliveryHome')">Delivery Home</span>
          </dt>
          <dd>
            <span>{{ buyBook.deliveryHome }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.deliveryAddress')">Delivery Address</span>
          </dt>
          <dd>
            <span>{{ buyBook.deliveryAddress }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.paymentDetails')">Payment Details</span>
          </dt>
          <dd>
            <span>{{ buyBook.paymentDetails }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.purchaseReview')">Purchase Review</span>
          </dt>
          <dd>
            <span>{{ buyBook.purchaseReview }}</span>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.book')">Book</span>
          </dt>
          <dd>
            <div v-if="buyBook.book">
              <router-link :to="{ name: 'BookView', params: { bookId: buyBook.book.id } }">{{ buyBook.book.name }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('buyBookApp.buyBook.store')">Store</span>
          </dt>
          <dd>
            <div v-if="buyBook.store">
              <router-link :to="{ name: 'StoreView', params: { storeId: buyBook.store.id } }">{{ buyBook.store.name }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="buyBook.id" :to="{ name: 'BuyBookEdit', params: { buyBookId: buyBook.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./buy-book-details.component.ts"></script>
