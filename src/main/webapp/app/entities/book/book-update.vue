<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="buyBookApp.book.home.createOrEditLabel"
          data-cy="BookCreateUpdateHeading"
          v-text="$t('buyBookApp.book.home.createOrEditLabel')"
        >
          Create or edit a Book
        </h2>
        <div>
          <div class="form-group" v-if="book.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="book.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.name')" for="book-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="book-name"
              data-cy="name"
              :class="{ valid: !$v.book.name.$invalid, invalid: $v.book.name.$invalid }"
              v-model="$v.book.name.$model"
              required
            />
            <div v-if="$v.book.name.$anyDirty && $v.book.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.book.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.description')" for="book-description">Description</label>
            <textarea
              class="form-control"
              name="description"
              id="book-description"
              data-cy="description"
              :class="{ valid: !$v.book.description.$invalid, invalid: $v.book.description.$invalid }"
              v-model="$v.book.description.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.pubYear')" for="book-pubYear">Pub Year</label>
            <input
              type="number"
              class="form-control"
              name="pubYear"
              id="book-pubYear"
              data-cy="pubYear"
              :class="{ valid: !$v.book.pubYear.$invalid, invalid: $v.book.pubYear.$invalid }"
              v-model.number="$v.book.pubYear.$model"
              required
            />
            <div v-if="$v.book.pubYear.$anyDirty && $v.book.pubYear.$invalid">
              <small class="form-text text-danger" v-if="!$v.book.pubYear.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.book.pubYear.min" v-text="$t('entity.validation.min', { min: 1800 })">
                This field should be at least 1800.
              </small>
              <small class="form-text text-danger" v-if="!$v.book.pubYear.max" v-text="$t('entity.validation.max', { max: 2022 })">
                This field cannot be longer than 2022 characters.
              </small>
              <small class="form-text text-danger" v-if="!$v.book.pubYear.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.cover')" for="book-cover">Cover</label>
            <div>
              <img
                v-bind:src="'data:' + book.coverContentType + ';base64,' + book.cover"
                style="max-height: 100px"
                v-if="book.cover"
                alt="book image"
              />
              <div v-if="book.cover" class="form-text text-danger clearfix">
                <span class="pull-left">{{ book.coverContentType }}, {{ byteSize(book.cover) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('cover', 'coverContentType', 'file_cover')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_cover"
                id="file_cover"
                data-cy="cover"
                v-on:change="setFileData($event, book, 'cover', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="cover"
              id="book-cover"
              data-cy="cover"
              :class="{ valid: !$v.book.cover.$invalid, invalid: $v.book.cover.$invalid }"
              v-model="$v.book.cover.$model"
            />
            <input type="hidden" class="form-control" name="coverContentType" id="book-coverContentType" v-model="book.coverContentType" />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.author')" for="book-author">Author</label>
            <select class="form-control" id="book-author" data-cy="author" name="author" v-model="book.author">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="book.author && userOption.id === book.author.id ? book.author : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('buyBookApp.book.publisher')" for="book-publisher">Publisher</label>
            <select class="form-control" id="book-publisher" data-cy="publisher" name="publisher" v-model="book.publisher">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="book.publisher && publisherOption.id === book.publisher.id ? book.publisher : publisherOption"
                v-for="publisherOption in publishers"
                :key="publisherOption.id"
              >
                {{ publisherOption.name }}
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
            :disabled="$v.book.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./book-update.component.ts"></script>
