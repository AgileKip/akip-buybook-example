<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('buyBookApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('buyBookApp.taskAddShippingInfo.purpose')" for="task-add-shipping-info-purpose"
                >Purpose</label
              >
              <input
                type="text"
                class="form-control"
                name="purpose"
                id="task-add-shipping-info-purpose"
                readonly
                data-cy="purpose"
                :class="{
                  valid: !$v.taskContext.buyBookBinding.buyBook.purpose.$invalid,
                  invalid: $v.taskContext.buyBookBinding.buyBook.purpose.$invalid,
                }"
                v-model="$v.taskContext.buyBookBinding.buyBook.purpose.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('buyBookApp.taskAddShippingInfo.deliveryAddress')"
                for="task-add-shipping-info-deliveryAddress"
                >Delivery Address</label
              >
              <input
                type="text"
                class="form-control"
                name="deliveryAddress"
                id="task-add-shipping-info-deliveryAddress"
                data-cy="deliveryAddress"
                :class="{
                  valid: !$v.taskContext.buyBookBinding.buyBook.deliveryAddress.$invalid,
                  invalid: $v.taskContext.buyBookBinding.buyBook.deliveryAddress.$invalid,
                }"
                v-model="$v.taskContext.buyBookBinding.buyBook.deliveryAddress.$model"
                required
              />
              <div
                v-if="
                  $v.taskContext.buyBookBinding.buyBook.deliveryAddress.$anyDirty &&
                  $v.taskContext.buyBookBinding.buyBook.deliveryAddress.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.buyBookBinding.buyBook.deliveryAddress.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button type="submit" v-on:click.prevent="complete()" class="btn btn-success" data-cy="complete">
          <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-add-shipping-info-execute.component.ts"></script>
