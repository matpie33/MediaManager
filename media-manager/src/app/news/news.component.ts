import { Component } from '@angular/core';
import newsJSON from './data/news.json'

@Component({
  selector: 'app-news',
  templateUrl: './news.component.html',
  styleUrls: ['./news.component.css']
})
export class NewsComponent {
  data = newsJSON.news;
}
